package com.neusoft.ehrss.liaoning.processor.thirdparty.dto;

import java.io.Serializable;

/**
 * 重置密码或者手机号
 * @author ZHOUHD
 *
 */
public class TPResetSensitiveInfoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String account;

	/**
	 * 操作类型：2-个人重置密码，1-企业重置密码统一信用代码，3-单位编号重置
	 */
	private String ctype;

	/**
	 * 手机号码
	 */
	private String mobilenumber;
	
	/**
	 * 新密码
	 */
	private String password;
	

	public TPResetSensitiveInfoDTO() {
		super();
	}

	public String getMobilenumber() {
		return mobilenumber;
	}

	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getCtype() {
		return ctype;
	}

	public void setCtype(String ctype) {
		this.ctype = ctype;
	}

}
