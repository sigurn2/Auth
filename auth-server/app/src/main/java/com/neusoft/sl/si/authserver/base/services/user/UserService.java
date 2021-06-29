package com.neusoft.sl.si.authserver.base.services.user;

import java.util.List;
import java.util.Set;

import com.neusoft.sl.si.authserver.base.domains.org.Organization;
import com.neusoft.sl.si.authserver.base.domains.resource.Menu;
import com.neusoft.sl.si.authserver.base.domains.role.Role;
import com.neusoft.sl.si.authserver.base.domains.user.User;

/**
 * 用户管理服务对象
 * 
 * @author wuyf
 * 
 */

public interface UserService {

	/**
	 * 登记用户名/密码认证用户
	 * 
	 * @param user
	 * @return
	 */
	User createUserWithPassWord(User user);
	User createUserWithPassWordZwfw(User user);
	public User createUserWithQuestion(User user);

	/**
	 * 修改用户信息
	 * 
	 * @param user
	 * @return
	 */
	User updateUser(User user);

	/**
	 * 按账号查找用户
	 * 
	 * @param account
	 * @return
	 */
	User findByAccount(String account);

	/**
	 * 更新redis缓存信息
	 * @param account
	 * @return
	 */
	User findByAccountPut(String account);

	/**
	 * 按照账户更新User缓存
	 * 
	 * @param account
	 * @return
	 */
	User updateAccountCache(String account);

	/**
	 * 按CA ID获取用户
	 * 
	 * @param caKey
	 * @return
	 */
	User findByCaKey(String caKey);

	/**
	 * 检查账号是否存在
	 * 
	 * @param account
	 * @return
	 */
	boolean isAccountExists(String account);

	/**
	 * 获取用户菜单
	 * 
	 * @param account
	 * @return
	 */
	Set<Menu> getUserMenus(String account);

	/**
	 * 根据用户名获取用户角色清单
	 * 
	 * @param account
	 * @return
	 */
	Set<String> findRoles(String account);

	/**
	 * 根据用户名查找其权限清单
	 * 
	 * @param username
	 * @return
	 */
	// TODO 实现
	// Set<String> findPermissions(String username);

	/**
	 * 查找用户所属组织机构
	 * 
	 * @param orgId
	 * @return
	 */
	Organization findOrganizationById(String orgId);

	/**
	 * 获取用户所属组织机构下级机构
	 * 
	 * @param orgId
	 * @return
	 */
	List<Organization> findAllPosterity(String orgId);

	/**
	 * 获取用户某系统下的菜单
	 * 
	 * @param appid
	 * @param role
	 * @return
	 */
	Set<Menu> getUserMenus(String appid, Role role);
	
	public void verifyPassWord(String account, String password);

	/**
	 * 修改密码
	 * 
	 * @param account
	 * @param oldPassWord
	 * @param newPassWord
	 */
	void updatePassWord(String account, String oldPassWord, String newPassWord);


	//App忘记密码
	void forgetPassWord(String account, String newPassWord);
	/**
	 * 根据账户和角色查询用户信息
	 * 
	 * @param account
	 * @param roles
	 * @return
	 */
	User findByAccountAndRole(String account, Set<Role> roles);

	/**
	 * 根据身份证号查询用户信息
	 * 
	 * @param idNumber
	 * @return
	 */
	User findByIdNumber(String idNumber);

	/**
	 * 根据手机号码查询用户信息
	 * 
	 * @param mobileNumber
	 * @return
	 */
	User findByMobileNumber(String mobileNumber);

	/**
	 * 根据user的扩展信息主单位编号查询User
	 * 
	 * @param 
	 * @param mainCompanyNum
	 * @return
	 */
	User findByMainCompanyNum(String mainCompanyNum);

	/**
	 * 通过主单位编号，查询该主单位编号的User数量
	 * 
	 * @param mainCompanyNum
	 * @return
	 */
	int countUserByMainCompanyNum(String mainCompanyNum);

	/**
	 * 通过id查询用户
	 * 
	 * @param id
	 * @return
	 */
	User findById(String id);

	/**
	 * 通过user_id删除用户
	 * 
	 * @param user
	 */
	void deleteUser(User user);

	/**
	 * 某个角色关联的所有用户
	 * 
	 * @param role
	 * @return
	 */
	List<User> findByRoles(Role role);
}
