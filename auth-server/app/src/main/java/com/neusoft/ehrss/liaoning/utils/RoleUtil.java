package com.neusoft.ehrss.liaoning.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.neusoft.sl.si.authserver.uaa.controller.role.RoleDTO;
import com.neusoft.sl.si.authserver.uaa.exception.BusinessException;

/**
 * 
 * // 企业CA用户 ROLE_CA_ENTERPRISE_USER // 机关事业CA用户 ROLE_CA_DEPARTMENT_USER // 劳动就业
 * ROLE_LABOUR_JY_ENTERPRISE_USER // 劳动监察 ROLE_LABOUR_JC_ENTERPRISE_USER // 劳动仲裁
 * ROLE_LABOUR_ZC_ENTERPRISE_USER // 人才 ROLE_LABOUR_RC_ENTERPRISE_USER
 * 
 * @author zhou.haidong
 *
 */
public class RoleUtil {

	/**
	 * 需要判断参保companysi有数据的。
	 */
	public static final List<String> socialRole = new ArrayList<String>(Arrays.asList("ROLE_CA_ENTERPRISE_USER", "ROLE_CA_DEPARTMENT_USER"));

	/**
	 * 允许的角色
	 */
	public static final List<String> allowRole = new ArrayList<String>(Arrays.asList("ROLE_CA_ENTERPRISE_USER", "ROLE_CA_DEPARTMENT_USER", "ROLE_LABOUR_JY_ENTERPRISE_USER", "ROLE_LABOUR_JC_ENTERPRISE_USER",
			"ROLE_LABOUR_ZC_ENTERPRISE_USER", "ROLE_LABOUR_RC_ENTERPRISE_USER"));

	// public static final String

	public static void allowRoles(List<RoleDTO> roles) {
		if (null != roles) {
			for (RoleDTO role : roles) {
				if (!RoleUtil.allowRole.contains(role.getName())) {
					throw new BusinessException("角色选择不正确，不在允许范围内");
				}
			}
		}
	}
}
