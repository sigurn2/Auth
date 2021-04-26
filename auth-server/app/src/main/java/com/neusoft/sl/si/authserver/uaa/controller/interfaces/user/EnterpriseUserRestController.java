package com.neusoft.sl.si.authserver.uaa.controller.interfaces.user;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.neusoft.ehrss.liaoning.utils.RoleUtil;
import com.neusoft.sl.girder.ddd.core.exception.ResourceNotFoundException;
import com.neusoft.sl.girder.utils.LongDateUtils;
import com.neusoft.sl.si.authserver.base.domains.ca.CaOperHistory;
import com.neusoft.sl.si.authserver.base.domains.ca.CaOperHistoryRepository;
import com.neusoft.sl.si.authserver.base.domains.company.Company;
import com.neusoft.sl.si.authserver.base.domains.company.CompanySi;
import com.neusoft.sl.si.authserver.base.domains.company.CompanySiRepository;
import com.neusoft.sl.si.authserver.base.domains.user.AccountExistsException;
import com.neusoft.sl.si.authserver.base.domains.user.EnterpriseUser;
import com.neusoft.sl.si.authserver.base.domains.user.User;
import com.neusoft.sl.si.authserver.base.domains.user.UserRepository;
import com.neusoft.sl.si.authserver.base.domains.user.company.UserCompanyEntity;
import com.neusoft.sl.si.authserver.base.domains.user.company.UserCompanyEntityRepository;
import com.neusoft.sl.si.authserver.base.services.company.CompanyService;
import com.neusoft.sl.si.authserver.base.services.redis.RedisAccountService;
import com.neusoft.sl.si.authserver.base.services.user.UserCustomService;
import com.neusoft.sl.si.authserver.base.services.user.UserService;
import com.neusoft.sl.si.authserver.uaa.controller.interfaces.user.dto.CompanyInfoAssembler;
import com.neusoft.sl.si.authserver.uaa.controller.interfaces.user.dto.CompanyInfoDTO;
import com.neusoft.sl.si.authserver.uaa.controller.interfaces.user.dto.EnterpriseUserDTO;
import com.neusoft.sl.si.authserver.uaa.controller.interfaces.user.dto.EnterpriseUserDTOAssembler;
import com.neusoft.sl.si.authserver.uaa.exception.BusinessException;
import com.neusoft.sl.si.authserver.uaa.log.UserLogManager;
import com.neusoft.sl.si.authserver.uaa.log.enums.SystemType;

import io.swagger.annotations.ApiOperation;

/**
 * 企业用户控制器
 * 
 * @author mojf
 * 
 */
@RestController
@RequestMapping("/user")
public class EnterpriseUserRestController {

	/** 日志 */
	private static final Logger log = LoggerFactory.getLogger(EnterpriseUserRestController.class);

	@Resource
	private UserService userService;

	@Resource(name = "userCustomService")
	private UserCustomService userCustomService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private CompanySiRepository companySiRepository;

	@Resource
	private RedisAccountService redisAccountService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CaOperHistoryRepository caOperHistoryRepository;

	@Autowired
	private UserCompanyEntityRepository userCompanyEntityRepository;

	/***
	 * 查询的是public库
	 * 
	 * @param companyNumber
	 * @return
	 */
	@ApiOperation(value = "GET根据单位编号获取单位信息", tags = "企业用户操作接口", notes = "根据单位编号获取单位信息EnterpriseUserRestController")
	@GetMapping(value = "/company/{companyNumber}")
	@PreAuthorize("hasAnyAuthority('ROLE_CA')")
	public CompanyInfoDTO getEnterprise(@PathVariable("companyNumber") String companyNumber) {
		log.debug("CA查询单位信息companyNumber={}", companyNumber);
		Company company = companyService.findByCompanyNumber(companyNumber);
		CompanyInfoDTO dto = CompanyInfoAssembler.toDTO(company);
		CompanySi companySi = companySiRepository.findByCompanyNumber(companyNumber);
		if (companySi == null) {// 未参保
			dto.setIsSocial("0");
		} else {// 已参保
			dto.setIsSocial("1");
		}
		return dto;
	}

	/**
	 * 创建企业用户
	 * 
	 * @param userDTO
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "POST创建企业用户", tags = "企业用户操作接口", notes = "创建企业用户EnterpriseUserRestController")
	@PostMapping(value = "/enterprise")
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAnyAuthority('ROLE_CA')")
	public void createEnterpriseUser(@RequestBody EnterpriseUserDTO userDTO, HttpServletRequest request, Principal principal) {
		OAuth2Authentication authentication = (OAuth2Authentication) principal;
		OAuth2Request auth2Request = authentication.getOAuth2Request();
		Validate.notBlank(userDTO.getAccount(), "account is null");
		log.debug("CA单位用户注册account={}", userDTO.getAccount());
		if (userDTO.getRoles() == null || userDTO.getRoles().isEmpty()) {
			throw new BusinessException("请选择角色");
		}
		RoleUtil.allowRoles(userDTO.getRoles());
		// 先查询账号account是否存在
		Company company = companyService.findByCompanyNumber(userDTO.getAccount());

		User user = userRepository.findByAccount(userDTO.getAccount());
		if (user != null) {
			if (user.isActivated()) {
				throw new BusinessException(userDTO.getAccount() + "此账号已经存在，无需再次创建");
			} else {
				throw new BusinessException(userDTO.getAccount() + "此账号已经存在，用户账号被禁用");
			}
		}

		// 默认绑定开户的单位
		CompanyInfoDTO dto = new CompanyInfoDTO();
		dto.setCompanyNumber(company.getCompanyNumber());
		if (userDTO.getAssociatedCompanys() == null) {
			List<CompanyInfoDTO> lists = new ArrayList<CompanyInfoDTO>();
			lists.add(dto);
			userDTO.setAssociatedCompanys(lists);
		} else {
			userDTO.getAssociatedCompanys().add(dto);
		}

		userDTO.setName(company.getName());// 设置单位名称
		user = EnterpriseUserDTOAssembler.crtfromDTO(userDTO);
		user.setExtension("ca_register");
		user.setMainCompanyNum(userDTO.getAccount());
		user.setPassword(userDTO.getPassword());
		// 保存user对象
		user = userCustomService.createEnterpriseUser(user);
		// 记录日志
		UserLogManager.saveRegisterLog(SystemType.Enterprise.toString(), auth2Request.getClientId(), user, request);
	}

	/**
	 * 按账号获取单位登记情况
	 * 
	 * @param account
	 * @return
	 */
	@ApiOperation(value = "GET按账号获取账号信息", tags = "企业用户操作接口", notes = "按账号获取账号信息EnterpriseUserRestController")
	@GetMapping(value = "/enterprise/{account}")
	@PreAuthorize("hasAnyAuthority('ROLE_CA')")
	public EnterpriseUserDTO getEnterpriseUserByAccount(@PathVariable("account") String account) {
		log.debug("CA查询账号开户情况account={}", account);
		EnterpriseUserDTO dto = new EnterpriseUserDTO();
		User user = userService.findByAccount(account);
		if (null == user) {
			Company company = companyService.findByCompanyNumber(account);
			List<UserCompanyEntity> e = userCompanyEntityRepository.findByCompanyId(company.getId().toString());
			if (1 < e.size()) {
				throw new AccountExistsException("单位编号：" + account + "已经被关联到多个用户！！！");
			}
			// 子账号
			if (1 == e.size()) {
				String userId = e.get(0).getUserid();
				User u = userRepository.findById(userId);
				if (u == null) {
					throw new ResourceNotFoundException("存在垃圾数据，单位编号：" + account + "存在关联关系，但是无账号信息");
				}
				dto = EnterpriseUserDTOAssembler.toDTO(u);
				dto.setQueryCompanyNumber(account);
				dto.setQueryCompanyAccountType("2");// 子账号
			} else {
				// 未开通网上业务
				dto.setQueryCompanyNumber(account);
				dto.setQueryCompanyAccountType("0");// 未开通
			}
			dto.setQueryCompanyName(company.getName());
		} else {
			// 主账号
			if (user instanceof EnterpriseUser) {
				dto = EnterpriseUserDTOAssembler.toDTO(user);
				dto.setQueryCompanyNumber(account);
				dto.setQueryCompanyAccountType("1");// 主账号
				dto.setQueryCompanyName(user.getName());
			} else {
				throw new ResourceNotFoundException("此账号：" + account + "非企业用户");
			}
		}
		CompanySi companySi = companySiRepository.findByCompanyNumber(account);
		if (companySi == null) {// 未参保
			dto.setQueryCompanyIsSocial("0");
		} else {// 已参保
			dto.setQueryCompanyIsSocial("1");
		}
		return dto;
	}

	@ApiOperation(value = "PUT绑定单位", tags = "企业用户操作接口", notes = "绑定单位EnterpriseUserRestController")
	@PutMapping(value = "/enterprise/company")
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAnyAuthority('ROLE_CA')")
	public void addCompanyForEnterpriseUser(@RequestBody EnterpriseUserDTO userDTO, HttpServletRequest request) {
		log.debug("CA绑定单位account={}", userDTO.getAccount());
		if (userDTO.getAssociatedCompanys() == null || userDTO.getAssociatedCompanys().isEmpty()) {
			throw new BusinessException("请输入绑定的单位");
		}
		User user = userService.findByAccount(userDTO.getAccount());
		if (null == user) {
			throw new ResourceNotFoundException("根据账号：" + userDTO.getAccount() + "未找到有效的用户");
		}
		if (!user.isActivated()) {
			throw new BusinessException("该账号已被禁用，请激活后继续操作");
		}
		if (user instanceof EnterpriseUser) {
			user = userCustomService.addCompanyForEnterpriseUser(user, userDTO.getAssociatedCompanys());
		} else {
			throw new ResourceNotFoundException("此账号：" + userDTO.getAccount() + "非企业用户");
		}
	}

	@ApiOperation(value = "PUT单位解绑", tags = "企业用户操作接口", notes = "单位解绑EnterpriseUserRestController")
	@DeleteMapping(value = "/enterprise/company")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAnyAuthority('ROLE_CA')")
	public void deleteCompanyForEnterpriseUser(@RequestBody EnterpriseUserDTO userDTO, HttpServletRequest request) {
		log.debug("CA单位解绑account={}", userDTO.getAccount());
		if (userDTO.getAssociatedCompanys() == null || userDTO.getAssociatedCompanys().isEmpty()) {
			throw new BusinessException("请输入解绑的单位");
		}
		User user = userService.findByAccount(userDTO.getAccount());
		if (null == user) {
			throw new ResourceNotFoundException("根据账号：" + userDTO.getAccount() + "未找到有效的用户");
		}
		if (!user.isActivated()) {
			throw new BusinessException("该账号已被禁用，请激活后继续操作");
		}
		if (user instanceof EnterpriseUser) {
			user = userCustomService.removeCompanyForEnterpriseUser(user, userDTO.getAssociatedCompanys());
		} else {
			throw new ResourceNotFoundException("此账号：" + userDTO.getAccount() + "非企业用户");
		}
	}

	@ApiOperation(value = "PUT禁用账号", tags = "企业用户操作接口", notes = "禁用账号不会删除数据EnterpriseUserRestController")
	@DeleteMapping(value = "/enterprise/activate/{account}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAnyAuthority('ROLE_CA')")
	public void inactiveUser(@PathVariable("account") String account) {
		log.debug("CA禁用账号account={}", account);
		User user = userService.findByAccount(account);
		if (null == user) {
			throw new ResourceNotFoundException("根据账号：" + account + "未找到有效的用户");
		}
		if (user instanceof EnterpriseUser) {
			if (!user.isActivated()) {
				throw new BusinessException("账号已经禁用，无需再次操作");
			}
			user.inactiveUser();
			user = userService.updateUser(user);
			redisAccountService.modifyOnlyRedisAccount(account);
		} else {
			throw new ResourceNotFoundException("此账号：" + account + "非企业用户");
		}
	}

	@ApiOperation(value = "PUT激活账号", tags = "企业用户操作接口", notes = "激活账号EnterpriseUserRestController")
	@PutMapping(value = "/enterprise/activate/{account}")
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAnyAuthority('ROLE_CA')")
	public void activeUser(@PathVariable("account") String account) {
		log.debug("CA激活账号account={}", account);
		User user = userService.findByAccount(account);
		if (null == user) {
			throw new ResourceNotFoundException("根据账号：" + account + "未找到有效的用户");
		}
		if (user instanceof EnterpriseUser) {
			if (user.isActivated()) {
				throw new BusinessException("账号已经激活，无需再次操作");
			}
			user.activeUser();
			user = userService.updateUser(user);
			redisAccountService.modifyOnlyRedisAccount(account);
		} else {
			throw new ResourceNotFoundException("此账号：" + account + "非企业用户");
		}
	}

	@ApiOperation(value = "PUT修改账号信息", tags = "企业用户操作接口", notes = "修改账号信息EnterpriseUserRestController")
	@PutMapping(value = "/enterprise")
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAnyAuthority('ROLE_CA')")
	public void modifyEnterpriseUser(@RequestBody EnterpriseUserDTO userDTO) {
		log.debug("CA修改账号信息account={}", userDTO.getAccount());
		User user = userService.findByAccount(userDTO.getAccount());
		if (null == user) {
			throw new ResourceNotFoundException("根据账号：" + userDTO.getAccount() + "未找到有效的用户");
		}
		if (!user.isActivated()) {
			throw new BusinessException("该账号已被禁用，请激活后继续操作");
		}
		if (user instanceof EnterpriseUser) {
			user = userCustomService.modifyEnterpriseUser(user, userDTO);
		} else {
			throw new ResourceNotFoundException("此账号：" + userDTO.getAccount() + "非企业用户");
		}
	}

	@ApiOperation(value = "DELETE账号注销", tags = "企业用户操作接口", notes = "账号注销会删除数据EnterpriseUserRestController")
	@DeleteMapping(value = "/enterprise/account/{account}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAnyAuthority('ROLE_CA')")
	public void deleteUser(@PathVariable("account") String account, Principal principal) {
		OAuth2Authentication authentication = (OAuth2Authentication) principal;
		OAuth2Request auth2Request = authentication.getOAuth2Request();
		log.debug("CA账号注销account={}", account);
		User user = userService.findByAccount(account);
		if (null == user) {
			throw new ResourceNotFoundException("根据账号：" + account + "未找到有效的用户");
		}
		if (user instanceof EnterpriseUser) {
			// 转移CA操作历史
			CaOperHistory history = new CaOperHistory();
			// Date date = new Date();
			if (!StringUtils.isEmpty(user.getRegistedDate())) {
				history.setCaOperTime(Long.parseLong(user.getRegistedDate().substring(0, 10)));
			}
			history.setCaOperType(auth2Request.getClientId());
			history.setRevokeTime(LongDateUtils.nowAsSecondLong().toString());
			history.setUserId(user.getId());
			history.setMainCompanyNumber(account);
			caOperHistoryRepository.save(history);
			// 删除User
			userRepository.delete(user);
			redisAccountService.modifyRedis(user.getAccount());
		} else {
			throw new ResourceNotFoundException("此账号：" + account + "非企业用户");
		}
	}

}
