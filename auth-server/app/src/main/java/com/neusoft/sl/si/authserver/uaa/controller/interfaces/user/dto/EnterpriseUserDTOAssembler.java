package com.neusoft.sl.si.authserver.uaa.controller.interfaces.user.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.neusoft.sl.si.authserver.base.domains.company.Company;
import com.neusoft.sl.si.authserver.base.domains.role.OperatorRole;
import com.neusoft.sl.si.authserver.base.domains.role.Role;
import com.neusoft.sl.si.authserver.base.domains.user.EnterpriseUserFactory;
import com.neusoft.sl.si.authserver.base.domains.user.User;
import com.neusoft.sl.si.authserver.uaa.controller.role.RoleDTO;
import com.neusoft.sl.si.authserver.uaa.controller.role.RoleDTOAssembler;

/**
 * simis 单位用户转换器
 * 
 * @author wuyf
 * 
 */
public class EnterpriseUserDTOAssembler {

	public static final String COMPANY_TYPE = "0";

	/**
	 * 从DTO创建
	 * 
	 * @param userDTO
	 * @return
	 */
	public static User crtfromDTO(EnterpriseUserDTO dto) {
		if (dto == null) {
			return null;
		}
		EnterpriseUserFactory factory = new EnterpriseUserFactory();
		User user = factory.createEnterpriseUser(COMPANY_TYPE, dto.getAccount(), dto.getName(), dto.getEmail(), dto.getMobile(), toCompanys(dto.getAssociatedCompanys()));
		user.setRoles(toRoles(dto.getRoles()));
		return user;
	}

	private static Set<Role> toRoles(List<RoleDTO> roleDTOs) {
		if (null == roleDTOs) {
			return null;
		} else {
			Set<Role> roles = new HashSet<Role>();
			for (RoleDTO dto : roleDTOs) {
				Role role = new OperatorRole(dto.getName(), "");
				roles.add(role);
			}
			return roles;
		}
	}

	/**
	 * 从DTO查询关联单位信息
	 * 
	 * @param associatedCompanys
	 * @return
	 */
	private static Set<Company> toCompanys(List<CompanyInfoDTO> associatedCompanys) {
		if (null == associatedCompanys) {
			return null;
		} else {
			Set<Company> companys = new HashSet<Company>();
			for (CompanyInfoDTO companyDto : associatedCompanys) {
				Company company = new Company();
				company.setCompanyNumber(companyDto.getCompanyNumber());
				companys.add(company);
			}
			return companys;
		}
	}

	/**
	 * 转换为DTO
	 * 
	 * @param value
	 * @return
	 */
	public static EnterpriseUserDTO toDTO(User value) {
		EnterpriseUserDTO dto = new EnterpriseUserDTO();
		dto.setAccount(value.getAccount());
		dto.setEmail(value.getEmail());
		dto.setMobile(value.getMobile());
		dto.setName(value.getName());
		if (value.getRoles() != null) {
			List<RoleDTO> roles = new ArrayList<RoleDTO>();
			for (Role role : value.getRoles()) {
				if (role != null) {
					roles.add(RoleDTOAssembler.toDTO(role));
				}
			}
			dto.setRoles(roles);
		}
		// dto.setCompanyType(COMPANY_TYPE);
		List<CompanyInfoDTO> infoDTOs = toAssociatedCompanys(value.getCompanys());
		if (infoDTOs != null && infoDTOs.size() > 0) {
			// 排序
			Collections.sort(infoDTOs, new Comparator<CompanyInfoDTO>() {
				@Override
				public int compare(CompanyInfoDTO u1, CompanyInfoDTO u2) {
					if (u1.getCompanyNumber().equals(value.getAccount())) {
						return -1;
					}
					return 0;
				}
			});
		}
		dto.setAssociatedCompanys(infoDTOs);
		dto.setActivated(value.isActivated());
		return dto;
	}

	/**
	 * 关联单位数据转换
	 * 
	 * @param companys
	 * @return
	 */
	private static List<CompanyInfoDTO> toAssociatedCompanys(Set<Company> companys) {
		if (companys == null)
			return null;
		List<CompanyInfoDTO> list = new ArrayList<CompanyInfoDTO>();
		CompanyInfoDTO dto = null;
		for (Company item : companys) {
			if (item != null) {
				dto = new CompanyInfoDTO();
				dto.setId(item.getId());
				dto.setCompanyNumber(item.getCompanyNumber());
				dto.setName(item.getName());
				dto.setOrgCode(item.getOrgCode());
				dto.setAreaCode(item.getAreaCode());
				dto.setTaxCode(item.getTaxCode());
				list.add(dto);
			}
		}
		return list;
	}

}
