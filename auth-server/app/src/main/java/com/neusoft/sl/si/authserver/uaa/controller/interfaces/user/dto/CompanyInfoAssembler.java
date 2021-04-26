package com.neusoft.sl.si.authserver.uaa.controller.interfaces.user.dto;

import com.neusoft.sl.si.authserver.base.domains.company.Company;
import com.neusoft.sl.si.authserver.base.domains.company.CompanySi;

public class CompanyInfoAssembler {

	public static CompanyInfoDTO toDTO(Company company) {
		if (company == null)
			return null;
		CompanyInfoDTO dto = new CompanyInfoDTO();
		dto.setId(company.getId());
		dto.setCompanyNumber(company.getCompanyNumber());
		dto.setName(company.getName());
//		dto.setOrgCode(company.getOrgCode());
//		dto.setTaxCode(company.getTaxCode());
//		dto.setAreaCode(company.getAreaCode());
		return dto;
	}

	public static CompanyInfoDTO toDTO(CompanySi company) {
		if (company == null)
			return null;
		CompanyInfoDTO dto = new CompanyInfoDTO();
		dto.setId(company.getId());
		dto.setCompanyNumber(company.getCompanyNumber());
		dto.setName(company.getName());
		dto.setOrgCode(company.getOrgCode());
		dto.setTaxCode(company.getTaxCode());
		dto.setAreaCode(company.getAreaCode());
		return dto;
	}

}
