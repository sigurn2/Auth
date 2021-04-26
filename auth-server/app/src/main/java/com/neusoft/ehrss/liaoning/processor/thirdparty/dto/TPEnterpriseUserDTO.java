package com.neusoft.ehrss.liaoning.processor.thirdparty.dto;

import java.io.Serializable;

public class TPEnterpriseUserDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 账户名 */
	private String account;

	/**
	 * 用户名
	 */
	private String name;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 是否激活
	 */
	private String active;
	/**
	 * 主单位编号
	 */
	private String mainCompanyNum;
	/**
	 * 角色类型 两位，预设角色类型 uuid，查询数据库设置
	 */
	private String roleType;
	
	/**
	 * 统一信用代码
	 */
	private String orgCode;

	/**
	 * @return the roleType
	 */
	public String getRoleType() {
		return roleType;
	}

	/**
	 * @param roleType
	 *            the roleType to set
	 */
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public String getMainCompanyNum() {
		return mainCompanyNum;
	}

	/**
	 * @return the active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active
	 *            the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}

	public void setMainCompanyNum(String mainCompanyNum) {
		this.mainCompanyNum = mainCompanyNum;
	}

	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * @param account
	 *            the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public TPEnterpriseUserDTO() {
		super();
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

}
