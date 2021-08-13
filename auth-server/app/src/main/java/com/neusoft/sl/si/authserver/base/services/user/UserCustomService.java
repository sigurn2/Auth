/**
 * 
 */
package com.neusoft.sl.si.authserver.base.services.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.neusoft.sl.si.authserver.base.domains.user.User;
import com.neusoft.sl.si.authserver.uaa.controller.interfaces.user.dto.AtmVerifyResultDTO;
import com.neusoft.sl.si.authserver.uaa.controller.interfaces.user.dto.CompanyInfoDTO;
import com.neusoft.sl.si.authserver.uaa.controller.interfaces.user.dto.EnterpriseUserDTO;
import com.neusoft.sl.si.authserver.uaa.controller.interfaces.user.dto.PassWordResetDetailDTO;

/**
 * 用户管理扩展服务对象
 * 
 * @author mojf
 *
 */
public interface UserCustomService {

	public void resetPasswordForExpert(PassWordResetDetailDTO passWordResetDetailDTO, HttpServletRequest request);

	public User createExpertUser(User user, String personNumber, HttpServletRequest request);

	public void resetPasswordForFace(HttpServletRequest request);
	public void updateIdNumberForZwfwApp(String account,String idNumber);
	public void updateEmailForZwfw(String account,String email);
	public void updateEmailForZwfwEnterprise(String orgcode,String email);
	public void resetPassword(PassWordResetDetailDTO passWordResetDetailDTO, HttpServletRequest request);
	public void updateNameForZwfwApp(String account,String name);
	public void updateUserForPersonAndRole(String idNumber);
	public void updateUserForEntAndRoleZwfw(String orgCode,String companyName);
	public void updateUserForEntAndRole(Long unitId);
	public void updateUserForPersonAndRoleByZwfw(String idNumber, String name);

	public void savePersonAuthedLogBack(User user, String authClient, String authType, String personCardNumber, String authDate);

	/**
	 * 登记企业用户
	 * 
	 * @param user
	 * @return
	 */
	User createEnterpriseUser(User user);



	/**
	 * 登记企业用户 参数userType，对应role
	 * 
	 * @param user
	 * @param userType
	 * @return
	 */
	// User createEnterpriseUser(User user, Set<String> userType);

	/**
	 * 登记企业用户 增加判断active状态，对应企业CA注销操作
	 * 
	 * @param user
	 * @return
	 */
	User createEnterpriseUserWithOrgCode(User user);

	public AtmVerifyResultDTO verifyPersonUserATM(String mobilenumber, String cardNumber);

	public User createPersonUserATM(User user);

	public User createPersonCloudbae(String username ,String idNumber, String name, String mobile, String extension,String email);

	// public User createPersonUserCloudbae(User user);

	public User createPerson(User user);
	public User createPersonBenxi(User user);
	public User createPersonZwfw(User user);
	/**
	 * 生成个人用户信息，同时绑定社保信息
	 * 
	 * @param user
	 * @return
	 */
	// User createPersonUserWithSiInfo(User user);

	/**
	 * 生成个人用户信息，同时绑定社保信息
	 * 
	 * @param user
	 * @param personNumber
	 * @return
	 */
	// User createPersonUserWithSiInfo(User user, String personNumber);

	/**
	 * 登记居民用户
	 * 
	 * @param user
	 * @return
	 */
	User createSiAgentUser(User user);

	/**
	 * 登记居民用户 增加用户类型，用于匹配角色
	 * 
	 * @param user
	 * @param userType
	 * @return
	 */
	User createSiAgentUserWithUserType(User user, String userType);

	/**
	 * 用户激活Ohwyaa平台
	 * 
	 * @param user
	 * 
	 * @return
	 */
	User activeOhwyaa(User user);

	public User addCompanyForEnterpriseUser(User user, List<CompanyInfoDTO> associatedCompanys);

	public User removeCompanyForEnterpriseUser(User user, List<CompanyInfoDTO> associatedCompanys);

	public User modifyEnterpriseUser(User user, EnterpriseUserDTO edto);
}
