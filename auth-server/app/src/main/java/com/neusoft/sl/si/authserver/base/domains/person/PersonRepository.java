package com.neusoft.sl.si.authserver.base.domains.person;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 人员仓储
 * 
 * @author mojf
 * 
 */
public interface PersonRepository extends JpaRepository<Person, Long> {

	/**
	 * 按身份证号、姓名查询人员信息，不判断参保状态
	 * 
	 * @param companyId
	 * @param certificateNumber
	 * @param name
	 * @return
	 */
	List<Person> findAllByCertificate(String certificateNumber, String name);

	/**
	 * 按身份证号查询人员信息
	 * 
	 * @param certificateNumber
	 * @return
	 */
	List<Person> findByCertificate(String certificateNumber);

	/**
	 * 按照个人编号，查询人员信息
	 * 
	 * @param personNumber
	 * @return
	 */
	List<Person> findByPersonNumber(PersonNumber personNumber);

	List<Person> findBySocialSecurityCardNumber(String socialSecurityCardNumber);

}
