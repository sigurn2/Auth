package com.neusoft.ehrss.liaoning.processor.thirdparty.dto;

import java.io.Serializable;

/**
 * 
 * @author mojf 查询用户社保信息DTO
 *
 */
public class TPQueryPersonSiInfoQueryDTO implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private String idNumber;
	private String name;

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public TPQueryPersonSiInfoQueryDTO() {
		super();
	}

	public TPQueryPersonSiInfoQueryDTO(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
