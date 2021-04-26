package com.neusoft.ehrss.liaoning.processor.thirdparty.dto;

import java.io.Serializable;

/**
 * 
 * @author mojf 查询用户社保信息DTO
 *
 */
public class TPQueryPersonSiInfoDTO implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private String idNumber;
	private String name;
	private String cardNumber;

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

	public TPQueryPersonSiInfoDTO() {
		super();
	}

	public TPQueryPersonSiInfoDTO(String idNumber, String name, String cardNumber) {
		this.idNumber = idNumber;
		this.name = name;
		this.cardNumber = cardNumber;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

}
