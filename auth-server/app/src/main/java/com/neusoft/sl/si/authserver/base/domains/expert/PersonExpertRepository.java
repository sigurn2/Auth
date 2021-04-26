package com.neusoft.sl.si.authserver.base.domains.expert;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 专家仓储
 * 
 * @author zhou.haidong
 * 
 */
public interface PersonExpertRepository extends JpaRepository<PersonExpert, String> {

	/**
	 * 
	 * @return
	 */
	PersonExpert findById(String id);

	/**
	 * 
	 * @param personNumber
	 * @return
	 */
	PersonExpert findByPersonNumber(String personNumber);

	/**
	 * 按身份证号查询专家信息
	 * 
	 * @param idNumber
	 * @return
	 */
	List<PersonExpert> findByIdNumber(String idNumber);

}
