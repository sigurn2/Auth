package com.neusoft.sl.si.authserver.base.services.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.sl.girder.ddd.core.exception.ResourceNotFoundException;
import com.neusoft.sl.si.authserver.base.domains.company.CompanyRepository;
import com.neusoft.sl.si.authserver.base.domains.org.Organization;
import com.neusoft.sl.si.authserver.base.domains.org.OrganizationRepository;
import com.neusoft.sl.si.authserver.base.domains.person.PersonRepository;
import com.neusoft.sl.si.authserver.base.domains.resource.Menu;
import com.neusoft.sl.si.authserver.base.domains.role.InitRoleProvider;
import com.neusoft.sl.si.authserver.base.domains.role.Role;
import com.neusoft.sl.si.authserver.base.domains.user.AccountExistsException;
import com.neusoft.sl.si.authserver.base.domains.user.User;
import com.neusoft.sl.si.authserver.base.domains.user.UserRepository;
import com.neusoft.sl.si.authserver.base.services.password.PasswordEncoderService;

/**
 * 用户管理实现
 * 
 * @author wuyf mojf y_zhang.neu
 * 
 */
@Service
public class  UserServiceImpl implements UserService {

	/** 日志 */
	protected static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	private static final String CACHE_USER = "CACHE_USER";
	// private static final String CACHE_MENU = "CACHE_MENU";

	@Resource
	private UserRepository userRepository;
	@Resource
	private PersonRepository personRepository;
	@Resource
	private CompanyRepository companyRepository;
	@Resource
	private OrganizationRepository organizationRepository;

	@Resource(name = "${saber.auth.password.encoder}")
	private PasswordEncoderService passwordEncoderService;
	@Resource(name = "enterpriseUserRoleProvider")
	private InitRoleProvider enterpriseUserRoleProvider;
	@Resource(name = "personUserRoleProvider")
	private InitRoleProvider personUserRoleProvider;
	@Resource(name = "siagentUserRoleProvider")
	private InitRoleProvider siagentUserRoleProvider;
	@Value("${security.basic.keys}")
	private String keys;

	/*
	 * (non-Javadoc) 更新账号信息,修改密码不是这个
	 * 
	 * @see
	 * com.neusoft.sl.si.sipub.security.service.UserService#update(com.neusoft
	 * .sl.si.sipub.security.domain.model.user.User)
	 */

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public User updateUser(User user) {
		Validate.notNull(user, "更新用户信息时，用户对象不能为空");
		return userRepository.save(user);
	}

	/*
	 * (non-Javadoc) 按账号查找用户
	 * 
	 * @see
	 * com.neusoft.sl.si.sipub.security.service.UserService#findByAccount(java
	 * .lang.String)
	 */

	@Cacheable(value = CACHE_USER, key = "#account")
	public User findByAccount(String account) {
		LOGGER.debug("按账号查找用户{}", account);
		Validate.notBlank(account, "按账号查找用户时，账号不能为空");
		return userRepository.findByAccount(account);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.sl.si.authserver.base.services.user.UserService#
	 * updateAccountCache(java.lang.String)
	 */
	@Override
	@CachePut(value = CACHE_USER, key = "#account")
	public User updateAccountCache(String account) {
		LOGGER.debug("按账号查找用户{}", account);
		Validate.notBlank(account, "按账号查找用户时，账号不能为空");
		return userRepository.findByAccount(account);
	}

	/**
	 * 按CA ID获取用户
	 * 
	 * @param caKey
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	// TODO Refactor用户管理
	public User findByCaKey(String caKey) {
		LOGGER.debug("按Cakey查找用户{}", caKey);
		return userRepository.findByCaKey(caKey);
	}

	/**
	 * 获取用户菜单
	 * 
	 * @param account
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public Set<Menu> getUserMenus(String account) {
		// 获取用户菜单
		User user = findByAccount(account);
		// 获取用户菜单
		Set<Menu> menus = new HashSet<Menu>();
		Set<Role> roles = user.getRoles();
		// 获取菜单清单
		for (Role role : roles) {
			menus.addAll(role.getMenus());
		}
		return menus;
	}

	/**
	 * 账号是否存在
	 * 
	 * @param account
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public boolean isAccountExists(String account) {
		User user = findByAccount(account);
		return null != user;
	}

	/*
	 * (non-Javadoc) 根据用户名获取用户角色清单
	 * 
	 * @see
	 * com.neusoft.sl.si.sipub.security.service.user.UserService#findRoles(java
	 * .lang.String)
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public Set<String> findRoles(String account) {
		// 获取用户菜单
		User user = findByAccount(account);
		return user.getRolesName();
	}

	/*
	 * (non-Javadoc) 注册新用户
	 * 
	 * @see com.neusoft.sl.si.sipub.security.service.user.UserService#
	 * createUserWithPassWord
	 * (com.neusoft.sl.si.sipub.security.domain.model.user.User)
	 */

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public User createUserWithPassWord(User user) {

		LOGGER.debug("user = {}",user.isActivated());
		// 验证用户账号是否存在
		if (isAccountExists(user.getAccount())) {
			throw new AccountExistsException(user.getAccount());
		}
		// 加密密码
		user.setActivated(true);
		user.setPassword(passwordEncoderService.encryptPassword(user.getPassword()));
		// 登记用户
		LOGGER.debug("roles = {}", user.getRoles());
		return userRepository.save(user);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public User createUserWithPassWordZwfw(User user) {

		LOGGER.debug("user = {}",user.isActivated());
		// 验证用户账号是否存在
		if (isAccountExists(user.getAccount())) {
			throw new AccountExistsException(user.getAccount());
		}
		// 加密密码
		user.setActivated(false);
		//user.setPassword(passwordEncoderService.encryptPassword(user.getPassword()));
		// 登记用户
		return userRepository.save(user);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public User createUserWithQuestion(User user) {
		// 验证用户账号是否存在
		// if (isAccountExists(user.getAccount())) {
		// throw new AccountExistsException(user.getAccount());
		// }
		// 加密密码
		user.setPassword(passwordEncoderService.encryptPassword(user.getPassword()));
		// 登记用户
		return userRepository.save(user);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public Organization findOrganizationById(String orgId) {
		return organizationRepository.findOne(orgId);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<Organization> findAllPosterity(String orgId) {
		List<Organization> result = organizationRepository.findSelfAndPosterity(orgId);

		// 排除自己
		if (result.size() > 0) {
			result.remove(0);
		}

		return result;
	}

	// @Cacheable(value = CACHE_MENU, key =
	// "#root.methodName+'-'+#appid+'-'+#role.id")
	public Set<Menu> getUserMenus(final String appid, final Role role) {
		// 获取用户菜单
		Set<Menu> menus = new HashSet<Menu>();
		if (role != null) {
			for (Menu menu : role.getMenus()) {
				if (appid != null && menu.getApp() != null && appid.equalsIgnoreCase(menu.getApp().getId())) {
					menus.add(menu);
				}
			}
		}
		return menus;
	}

	public void verifyPassWord(String account, String password) {
		User userInfo = this.findByAccount(account);
		// 校验旧密码
		if (!passwordEncoderService.matches(password, userInfo.getPassword())) {
			throw new BadCredentialsException("登录密码错误");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neusoft.sl.saber.authserver.service.user.UserService#updatePassWord(
	 * java.lang.String, java.lang.String, java.lang.String)
	 */

	public void updatePassWord(String account, String oldPassWord, String newPassWord) {
		User userInfo = this.findByAccount(account);
		// 校验旧密码
		if (passwordEncoderService.matches(oldPassWord, userInfo.getPassword())) {
			LOGGER.debug("==matches " + oldPassWord + "======" + userInfo.getPassword() + "=======");
			// 修改密码
			userInfo.setPassword(passwordEncoderService.encryptPassword(newPassWord));
			LOGGER.debug("==updateUser " + userInfo + "=============");
			List<String> key = new ArrayList<String>(Arrays.asList(keys.split(",")));
			// 如果设置密码对称加密keys
			if (key.size() >= 3) {
				userInfo.setSalt(DESUtil.strEnc(newPassWord, key.get(0), key.get(1), key.get(2)));
			}
			this.updateUser(userInfo);
		} else {
			throw new BadCredentialsException("您输入的旧密码有误，请重新输入");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.sl.saber.authserver.service.user.UserService#
	 * findByAccountAndRole(java.lang.String, java.util.Set)
	 */

	public User findByAccountAndRole(String account, Set<Role> roles) {
		User user = this.findByAccount(account);
		user.setRoles(roles);
		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.sl.saber.authserver.service.user.UserService#
	 * findByPersonIdNumber(java.lang.String)
	 */

	public User findByIdNumber(String idNumber) {
		LOGGER.debug("按证件号码查找用户{}", idNumber);
		Validate.notBlank(idNumber, "按证件号码查找用户时，证件号码不能为空");
		return userRepository.findByIdNumber(idNumber);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.sl.saber.authserver.service.user.UserService#
	 * findByMobileNumber(java.lang.String)
	 */

	public User findByMobileNumber(String mobileNumber) {
		LOGGER.debug("按手机号码查找用户{}", mobileNumber);
		Validate.notBlank(mobileNumber, "按手机号码查找用户时，手机号码不能为空");
		return userRepository.findByMobile(mobileNumber);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.sl.saber.authserver.service.user.UserService#
	 * findByMainCompanyNum(java.lang.String)
	 */

	public User findByMainCompanyNum(String mainCompanyNum) {
		List<User> userList = userRepository.findByMainCompanyNum(mainCompanyNum);
		if (null == userList || 0 == userList.size()) {
			throw new ResourceNotFoundException("根据主单位编号" + mainCompanyNum + "未查找到数据");
		}
		return userList.get(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neusoft.sl.saber.authserver.service.user.UserService#
	 * findById(java.lang.String)
	 */

	public User findById(String id) {
		User user = userRepository.findById(id);
		return user;
	}

	public void deleteUser(User user) {
		userRepository.delete(user);
	}

	public int countUserByMainCompanyNum(String mainCompanyNum) {
		List<User> userList = userRepository.findByMainCompanyNum(mainCompanyNum);
		return userList.size();
	}

	@Override
	public List<User> findByRoles(Role role) {
		List<User> userList = userRepository.findByRoles(role);
		return userList;
	}

	@Override
	@CachePut(value = CACHE_USER, key = "#account")
	public User findByAccountPut(String account) {
		return userRepository.findByAccount(account);
	}
}
