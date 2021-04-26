package com.neusoft.sl.si.authserver.uaa.controller.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.neusoft.sl.si.authserver.uaa.controller.interfaces.user.dto.CompanyInfoDTO;
import com.neusoft.sl.si.authserver.uaa.controller.role.RoleDTO;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 企业用户DTO
 * 
 * @author wuyf
 * 
 */
@JsonIgnoreProperties({ "email", "mobile" })
public class EnterpriseUserDTO implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3150254769450231607L;

	/** 账户名 */
	@ApiModelProperty(value = "账户名")
	@NotNull
	private String account;

	/**
	 * 是否可用
	 */
	private boolean activated = false;

	/**
	 * 用户名
	 */
	@ApiModelProperty(value = "用户名")
	@NotNull
	private String name;

	/** 电子邮件 */
	@ApiModelProperty(value = "电子邮件")
	@Email
	private String email;

	/**
	 * 负责人手机号码
	 */
	@ApiModelProperty(value = "负责人手机号码")
	private String mobile;

	/** 角色列表 */
	@ApiModelProperty(value = "角色列表")
	private List<RoleDTO> roles;

	private String orgCode ;


	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/** 关联单位 */
	@ApiModelProperty(value = "关联单位")
	private List<CompanyInfoDTO> associatedCompanys;

	private String password;

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

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile
	 *            the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public EnterpriseUserDTO(String account, String name, String mobile, String email, List<CompanyInfoDTO> associatedCompanys) {
		super();
		this.account = account;
		this.name = name;
		this.email = email;
		this.mobile = mobile;
		this.associatedCompanys = associatedCompanys;
	}

	public EnterpriseUserDTO() {
		super();
	}

	/**
	 * @return the roles
	 */
	public List<RoleDTO> getRoles() {
		return roles;
	}

	/**
	 * @param roles
	 *            the roles to set
	 */
	public void setRoles(List<RoleDTO> roles) {
		this.roles = roles;
	}

	/**
	 * @return the associatedCompanys
	 */
	public List<CompanyInfoDTO> getAssociatedCompanys() {
		return associatedCompanys;
	}

	/**
	 * @param associatedCompanys
	 *            the associatedCompanys to set
	 */
	public void setAssociatedCompanys(List<CompanyInfoDTO> associatedCompanys) {
		this.associatedCompanys = associatedCompanys;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
