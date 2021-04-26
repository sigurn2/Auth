package com.neusoft.sl.si.authserver.base.domains.family;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ThinUserFamilyRepository extends JpaRepository<ThinUserFamily, String>, JpaSpecificationExecutor<ThinUserFamily> {

	/**
	 * 按账号查找用户
	 * 
	 * @param account
	 * @return
	 */
	ThinUserFamily findByAccount(String account);

	/**
	 * 按证件号码查找用户
	 * 
	 * @param idNumber
	 * @return
	 */
	ThinUserFamily findByIdNumber(String idNumber);

	/**
	 * 按手机号码查询用户
	 * 
	 * @param mobileNumber
	 * @return
	 */
	ThinUserFamily findByMobile(String mobileNumber);

	/**
	 * 按照邮箱查询用户
	 * 
	 * @param email
	 * @return
	 */
	ThinUserFamily findByEmail(String email);

}
