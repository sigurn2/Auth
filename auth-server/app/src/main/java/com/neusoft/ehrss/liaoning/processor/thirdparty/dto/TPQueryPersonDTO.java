package com.neusoft.ehrss.liaoning.processor.thirdparty.dto;

import java.io.Serializable;

/**
 * 
 * @author mojf 查询用户信息DTO
 *
 */
public class TPQueryPersonDTO implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private String idNumber;
	private String name;
	private String mobile;

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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public TPQueryPersonDTO() {
		super();
	}

	public TPQueryPersonDTO(String idNumber, String name, String mobile) {
		this.idNumber = idNumber;
		this.name = name;
		this.mobile = mobile;
	}

}
