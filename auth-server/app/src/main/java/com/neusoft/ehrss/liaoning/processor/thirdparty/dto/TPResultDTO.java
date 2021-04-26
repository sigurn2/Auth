package com.neusoft.ehrss.liaoning.processor.thirdparty.dto;

import java.io.Serializable;

public class TPResultDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// insuredState

	private String orgCode;

	private String name;

	private String idNumber;

	private String mobilenumber;

	private String account;

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getMobilenumber() {
		return mobilenumber;
	}

	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

}
