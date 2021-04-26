package com.neusoft.ehrss.liaoning.processor.thirdparty.dto;

import java.io.Serializable;

/**
 * 
 * @author mojf 查询用户信息DTO
 *
 */
public class TPQueryPersonQueryDTO implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private String idNumber;

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public TPQueryPersonQueryDTO() {
		super();
	}

	public TPQueryPersonQueryDTO(String idNumber) {
		this.idNumber = idNumber;
	}

}
