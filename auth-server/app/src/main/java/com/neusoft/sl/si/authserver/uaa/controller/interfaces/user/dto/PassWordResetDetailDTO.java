package com.neusoft.sl.si.authserver.uaa.controller.interfaces.user.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * 密码重置数据传输对象
 * 
 * @author mojf
 *
 */
public class PassWordResetDetailDTO {
	/** 手机号码 */
	@ApiModelProperty(value = "手机号码")
	private String mobilenumber;
	/** 新密码 */
	@ApiModelProperty(value = "新密码")
	private String newPassword;
	/** 身份证号码 **/
	@ApiModelProperty(value = "身份证号码")
	private String idNumber;
	/** 手机号码 */

	/** 用户名**/
	private String username;

	private String name;

	private String personNumber;

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	/**
	 * @return the mobilenumber
	 */
	public String getMobilenumber() {
		return mobilenumber;
	}

	/**
	 * @param mobilenumber
	 *            the mobilenumber to set
	 */
	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
	}

	/**
	 * @return the newPassword
	 */
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * @param newPassword
	 *            the newPassword to set
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPersonNumber() {
		return personNumber;
	}

	public void setPersonNumber(String personNumber) {
		this.personNumber = personNumber;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
