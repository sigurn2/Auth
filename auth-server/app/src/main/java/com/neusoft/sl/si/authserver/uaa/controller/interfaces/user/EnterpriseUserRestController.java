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
 * ?????????????????????
 * 
 * @author mojf
 * 
 */
@RestController
@RequestMapping("/user")
public class EnterpriseUserRestController {

	/** ?????? */
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
	 * ????????????public???
	 * 
	 * @param companyNumber
	 * @return
	 */
	@ApiOperation(value = "GET????????????????????????????????????", tags = "????????????????????????", notes = "????????????????????????????????????EnterpriseUserRestController")
	@GetMapping(value = "/company/{companyNumber}")
	@PreAuthorize("hasAnyAuthority('ROLE_CA')")
	public CompanyInfoDTO getEnterprise(@PathVariable("companyNumber") String companyNumber) {
		log.debug("CA??????????????????companyNumber={}", companyNumber);
		Company company = companyService.findByCompanyNumber(companyNumber);
		CompanyInfoDTO dto = CompanyInfoAssembler.toDTO(company);
		CompanySi companySi = companySiRepository.findByCompanyNumber(companyNumber);
		if (companySi == null) {// ?????????
			dto.setIsSocial("0");
		} else {// ?????????
			dto.setIsSocial("1");
		}
		return dto;
	}

	/**
	 * ??????????????????
	 * 
	 * @param userDTO
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "POST??????????????????", tags = "????????????????????????", notes = "??????????????????EnterpriseUserRestController")
	@PostMapping(value = "/enterprise")
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAnyAuthority('ROLE_CA')")
	public void createEnterpriseUser(@RequestBody EnterpriseUserDTO userDTO, HttpServletRequest request, Principal principal) {
		OAuth2Authentication authentication = (OAuth2Authentication) principal;
		OAuth2Request auth2Request = authentication.getOAuth2Request();
		Validate.notBlank(userDTO.getAccount(), "account is null");
		log.debug("CA??????????????????account={}", userDTO.getAccount());
		if (userDTO.getRoles() == null || userDTO.getRoles().isEmpty()) {
			throw new BusinessException("???????????????");
		}
		RoleUtil.allowRoles(userDTO.getRoles());
		// ???????????????account????????????
		Company company = companyService.findByCompanyNumber(userDTO.getAccount());

		User user = userRepository.findByAccount(userDTO.getAccount());
		if (user != null) {
			if (user.isActivated()) {
				throw new BusinessException(userDTO.getAccount() + "??????????????????????????????????????????");
			} else {
				throw new BusinessException(userDTO.getAccount() + "?????????????????????????????????????????????");
			}
		}

		// ???????????????????????????
		CompanyInfoDTO dto = new CompanyInfoDTO();
		dto.setCompanyNumber(company.getCompanyNumber());
		if (userDTO.getAssociatedCompanys() == null) {
			List<CompanyInfoDTO> lists = new ArrayList<CompanyInfoDTO>();
			lists.add(dto);
			userDTO.setAssociatedCompanys(lists);
		} else {
			userDTO.getAssociatedCompanys().add(dto);
		}

		userDTO.setName(company.getName());// ??????????????????
		user = EnterpriseUserDTOAssembler.crtfromDTO(userDTO);
		user.setExtension("ca_register");
		user.setMainCompanyNum(userDTO.getAccount());
		user.setPassword(userDTO.getPassword());
		// ??????user??????
		user = userCustomService.createEnterpriseUser(user);
		// ????????????
		UserLogManager.saveRegisterLog(SystemType.Enterprise.toString(), auth2Request.getClientId(), user, request);
	}

	/**
	 * ?????????????????????????????????
	 * 
	 * @param account
	 * @return
	 */
	@ApiOperation(value = "GET???????????????????????????", tags = "????????????????????????", notes = "???????????????????????????EnterpriseUserRestController")
	@GetMapping(value = "/enterprise/{account}")
	@PreAuthorize("hasAnyAuthority('ROLE_CA')")
	public EnterpriseUserDTO getEnterpriseUserByAccount(@PathVariable("account") String account) {
		log.debug("CA????????????????????????account={}", account);
		EnterpriseUserDTO dto = new EnterpriseUserDTO();
		User user = userService.findByAccount(account);
		if (null == user) {
			Company company = companyService.findByCompanyNumber(account);
			List<UserCompanyEntity> e = userCompanyEntityRepository.findByCompanyId(company.getId().toString());
			if (1 < e.size()) {
				throw new AccountExistsException("???????????????" + account + "???????????????????????????????????????");
			}
			// ?????????
			if (1 == e.size()) {
				String userId = e.get(0).getUserid();
				User u = userRepository.findById(userId);
				if (u == null) {
					throw new ResourceNotFoundException("????????????????????????????????????" + account + "??????????????????????????????????????????");
				}
				dto = EnterpriseUserDTOAssembler.toDTO(u);
				dto.setQueryCompanyNumber(account);
				dto.setQueryCompanyAccountType("2");// ?????????
			} else {
				// ?????????????????????
				dto.setQueryCompanyNumber(account);
				dto.setQueryCompanyAccountType("0");// ?????????
			}
			dto.setQueryCompanyName(company.getName());
		} else {
			// ?????????
			if (user instanceof EnterpriseUser) {
				dto = EnterpriseUserDTOAssembler.toDTO(user);
				dto.setQueryCompanyNumber(account);
				dto.setQueryCompanyAccountType("1");// ?????????
				dto.setQueryCompanyName(user.getName());
			} else {
				throw new ResourceNotFoundException("????????????" + account + "???????????????");
			}
		}
		CompanySi companySi = companySiRepository.findByCompanyNumber(account);
		if (companySi == null) {// ?????????
			dto.setQueryCompanyIsSocial("0");
		} else {// ?????????
			dto.setQueryCompanyIsSocial("1");
		}
		return dto;
	}

	@ApiOperation(value = "PUT????????????", tags = "????????????????????????", notes = "????????????EnterpriseUserRestController")
	@PutMapping(value = "/enterprise/company")
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAnyAuthority('ROLE_CA')")
	public void addCompanyForEnterpriseUser(@RequestBody EnterpriseUserDTO userDTO, HttpServletRequest request) {
		log.debug("CA????????????account={}", userDTO.getAccount());
		if (userDTO.getAssociatedCompanys() == null || userDTO.getAssociatedCompanys().isEmpty()) {
			throw new BusinessException("????????????????????????");
		}
		User user = userService.findByAccount(userDTO.getAccount());
		if (null == user) {
			throw new ResourceNotFoundException("???????????????" + userDTO.getAccount() + "????????????????????????");
		}
		if (!user.isActivated()) {
			throw new BusinessException("????????????????????????????????????????????????");
		}
		if (user instanceof EnterpriseUser) {
			user = userCustomService.addCompanyForEnterpriseUser(user, userDTO.getAssociatedCompanys());
		} else {
			throw new ResourceNotFoundException("????????????" + userDTO.getAccount() + "???????????????");
		}
	}

	@ApiOperation(value = "PUT????????????", tags = "????????????????????????", notes = "????????????EnterpriseUserRestController")
	@DeleteMapping(value = "/enterprise/company")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAnyAuthority('ROLE_CA')")
	public void deleteCompanyForEnterpriseUser(@RequestBody EnterpriseUserDTO userDTO, HttpServletRequest request) {
		log.debug("CA????????????account={}", userDTO.getAccount());
		if (userDTO.getAssociatedCompanys() == null || userDTO.getAssociatedCompanys().isEmpty()) {
			throw new BusinessException("????????????????????????");
		}
		User user = userService.findByAccount(userDTO.getAccount());
		if (null == user) {
			throw new ResourceNotFoundException("???????????????" + userDTO.getAccount() + "????????????????????????");
		}
		if (!user.isActivated()) {
			throw new BusinessException("????????????????????????????????????????????????");
		}
		if (user instanceof EnterpriseUser) {
			user = userCustomService.removeCompanyForEnterpriseUser(user, userDTO.getAssociatedCompanys());
		} else {
			throw new ResourceNotFoundException("????????????" + userDTO.getAccount() + "???????????????");
		}
	}

	@ApiOperation(value = "PUT????????????", tags = "????????????????????????", notes = "??????????????????????????????EnterpriseUserRestController")
	@DeleteMapping(value = "/enterprise/activate/{account}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAnyAuthority('ROLE_CA')")
	public void inactiveUser(@PathVariable("account") String account) {
		log.debug("CA????????????account={}", account);
		User user = userService.findByAccount(account);
		if (null == user) {
			throw new ResourceNotFoundException("???????????????" + account + "????????????????????????");
		}
		if (user instanceof EnterpriseUser) {
			if (!user.isActivated()) {
				throw new BusinessException("???????????????????????????????????????");
			}
			user.inactiveUser();
			user = userService.updateUser(user);
			redisAccountService.modifyOnlyRedisAccount(account);
		} else {
			throw new ResourceNotFoundException("????????????" + account + "???????????????");
		}
	}

	@ApiOperation(value = "PUT????????????", tags = "????????????????????????", notes = "????????????EnterpriseUserRestController")
	@PutMapping(value = "/enterprise/activate/{account}")
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAnyAuthority('ROLE_CA')")
	public void activeUser(@PathVariable("account") String account) {
		log.debug("CA????????????account={}", account);
		User user = userService.findByAccount(account);
		if (null == user) {
			throw new ResourceNotFoundException("???????????????" + account + "????????????????????????");
		}
		if (user instanceof EnterpriseUser) {
			if (user.isActivated()) {
				throw new BusinessException("???????????????????????????????????????");
			}
			user.activeUser();
			user = userService.updateUser(user);
			redisAccountService.modifyOnlyRedisAccount(account);
		} else {
			throw new ResourceNotFoundException("????????????" + account + "???????????????");
		}
	}

	@ApiOperation(value = "PUT??????????????????", tags = "????????????????????????", notes = "??????????????????EnterpriseUserRestController")
	@PutMapping(value = "/enterprise")
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAnyAuthority('ROLE_CA')")
	public void modifyEnterpriseUser(@RequestBody EnterpriseUserDTO userDTO) {
		log.debug("CA??????????????????account={}", userDTO.getAccount());
		User user = userService.findByAccount(userDTO.getAccount());
		if (null == user) {
			throw new ResourceNotFoundException("???????????????" + userDTO.getAccount() + "????????????????????????");
		}
		if (!user.isActivated()) {
			throw new BusinessException("????????????????????????????????????????????????");
		}
		if (user instanceof EnterpriseUser) {
			user = userCustomService.modifyEnterpriseUser(user, userDTO);
		} else {
			throw new ResourceNotFoundException("????????????" + userDTO.getAccount() + "???????????????");
		}
	}

	@ApiOperation(value = "DELETE????????????", tags = "????????????????????????", notes = "???????????????????????????EnterpriseUserRestController")
	@DeleteMapping(value = "/enterprise/account/{account}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAnyAuthority('ROLE_CA')")
	public void deleteUser(@PathVariable("account") String account, Principal principal) {
		OAuth2Authentication authentication = (OAuth2Authentication) principal;
		OAuth2Request auth2Request = authentication.getOAuth2Request();
		log.debug("CA????????????account={}", account);
		User user = userService.findByAccount(account);
		if (null == user) {
			throw new ResourceNotFoundException("???????????????" + account + "????????????????????????");
		}
		if (user instanceof EnterpriseUser) {
			// ??????CA????????????
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
			// ??????User
			userRepository.delete(user);
			redisAccountService.modifyRedis(user.getAccount());
		} else {
			throw new ResourceNotFoundException("????????????" + account + "???????????????");
		}
	}

}
