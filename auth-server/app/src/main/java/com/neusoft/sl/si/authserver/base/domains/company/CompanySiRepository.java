package com.neusoft.sl.si.authserver.base.domains.company;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 单位仓储
 * 
 * @author mojf
 * 
 */
public interface CompanySiRepository extends JpaRepository<CompanySi, Long> {

    /**
     * 根据单位编号查询
     * 
     * @param companyNumber
     * @return
     */
	CompanySi findByCompanyNumber(String companyNumber);

	CompanySi findById(Long id);
}
