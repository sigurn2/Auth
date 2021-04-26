package com.neusoft.ehrss.liaoning.provider.ecard.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EcardValidResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String personNumber;
	private String signLevel;
	private Boolean isSiType;
	private String sscNo;
	private String idNumber;
	private String name;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
