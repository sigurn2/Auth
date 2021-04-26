package com.neusoft.ehrss.liaoning.security.implicit.alipay.controller.dto;

import java.io.Serializable;

public class AuthorizeCodeUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String personNumber;
	private String signLevel;
	private Boolean isSiType;
	private String sscNo;
	private String idNumber;
	private String clientId;
	private String name;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	private String type;

	public String getPersonNumber() {
		return personNumber;
	}

	public void setPersonNumber(String personNumber) {
		this.personNumber = personNumber;
	}

	public String getSignLevel() {
		return signLevel;
	}

	public void setSignLevel(String signLevel) {
		this.signLevel = signLevel;
	}

	public Boolean isIsSiType() {
		return isSiType;
	}

	public void setSiType(Boolean isSiType) {
		this.isSiType = isSiType;
	}

	public String getSscNo() {
		return sscNo;
	}

	public void setSscNo(String sscNo) {
		this.sscNo = sscNo;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
