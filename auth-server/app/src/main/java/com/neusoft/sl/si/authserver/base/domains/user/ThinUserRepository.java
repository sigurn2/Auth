package com.neusoft.sl.si.authserver.base.domains.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 用户仓储
 * 
 * @author WUYF
 * 
 */
public interface ThinUserRepository extends JpaRepository<ThinUser, String>, JpaSpecificationExecutor<ThinUser> {

	/**
	 * 按账号查找用户
	 * 
	 * @param account
	 * @return
	 */
	ThinUser findByAccount(String account);

	/**
	 * 按证件号码查找用户
	 * 
	 * @param idNumber
	 * @return
	 */
	ThinUser findByIdNumber(String idNumber);

	/**
	 * 按手机号码查询用户
	 * 
	 * @param mobileNumber
	 * @return
	 */
	ThinUser findByMobile(String mobileNumber);

	/**
	 * 按照邮箱查询用户
	 * 
	 * @param email
	 * @return
	 */
	ThinUser findByEmail(String email);

	ThinUser findByOrgCode(String orgCode);

	ThinUser findByExpertAccount(String expertAccount);
}
