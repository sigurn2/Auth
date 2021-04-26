package com.neusoft.ehrss.liaoning.processor.thirdparty.dto;


import java.io.Serializable;

public class TPChangePasswordUserDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String account;
	
	/**
	 * 操作类型：2-个人修改密码，1-企业修改密码
	 */
	private String ctype;

	/**
	 * 旧密码
	 */
	private String oldpassword;
	
	/**
	 * 新密码
	 */
	private String newpassword;
	

	public TPChangePasswordUserDTO() {
		super();
	}

	public String getOldpassword() {
		return oldpassword;
	}

	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}

	public String getNewpassword() {
		return newpassword;
	}

	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
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
