package com.neusoft.sl.si.authserver.base.domains.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ThinUserPatternRepository extends JpaRepository<ThinUserPattern, String>, JpaSpecificationExecutor<ThinUserPattern> {

	/**
	 * 按账号查找用户
	 * 
	 * @param account
	 * @return
	 */
	ThinUserPattern findByAccount(String account);

	/**
	 * 按证件号码查找用户
	 * 
	 * @param idNumber
	 * @return
	 */
	ThinUserPattern findByIdNumber(String idNumber);

	/**
	 * 按手机号码查询用户
	 * 
	 * @param mobileNumber
	 * @return
	 */
	ThinUserPattern findByMobile(String mobileNumber);

	/**
	 * 按照邮箱查询用户
	 * 
	 * @param email
	 * @return
	 */
	ThinUserPattern findByEmail(String email);

}
