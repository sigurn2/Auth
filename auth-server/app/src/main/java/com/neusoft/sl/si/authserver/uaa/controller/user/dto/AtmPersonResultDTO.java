package com.neusoft.sl.si.authserver.uaa.controller.user.dto;

import com.neusoft.sl.girder.security.oauth2.domain.PersonDTO;

public class AtmPersonResultDTO extends PersonDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 身份证号码 */
	private String idNumber;

	private String retireStatus;

	private String cardNumber;

	public String getRetireStatus() {
		return retireStatus;
	}

	public void setRetireStatus(String retireStatus) {
		this.retireStatus = retireStatus;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

}
