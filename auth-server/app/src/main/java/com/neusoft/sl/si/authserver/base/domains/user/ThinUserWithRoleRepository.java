package com.neusoft.sl.si.authserver.base.domains.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 用户仓储
 * 
 * @author WUYF
 * 
 */
public interface ThinUserWithRoleRepository extends JpaRepository<ThinUserWithRole, String>, JpaSpecificationExecutor<ThinUserWithRole> {

	/**
	 * 按账号查找用户
	 * 
	 * @param account
	 * @return
	 */
	ThinUserWithRole findByAccount(String account);

	/**
	 * 按证件号码查找用户
	 * 
	 * @param idNumber
	 * @return
	 */
	ThinUserWithRole findByIdNumber(String idNumber);

	/**
	 * 按手机号码查询用户
	 * 
	 * @param mobileNumber
	 * @return
	 */
	ThinUserWithRole findByMobile(String mobileNumber);

	/**
	 * 按照邮箱查询用户
	 * 
	 * @param email
	 * @return
	 */
	ThinUserWithRole findByEmail(String email);

	ThinUserWithRole findByOrgCode(String orgCode);

	ThinUserWithRole findByExpertAccount(String expertAccount);
}
