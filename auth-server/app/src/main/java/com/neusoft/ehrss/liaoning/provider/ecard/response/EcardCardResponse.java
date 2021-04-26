package com.neusoft.ehrss.liaoning.provider.ecard.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EcardCardResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 发卡地区行政区划代码
	 */
	private String areaCode;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 卡面社会保障号码
	 */
	private String siNo;
	/**
	 * 社会保障卡卡号
	 */
	private String sscNo;
	/**
	 * 识别码
	 */
	private String code;
	/**
	 * 卡应用状态
	 */
	private String status;

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSiNo() {
		return siNo;
	}

	public void setSiNo(String siNo) {
		this.siNo = siNo;
	}

	public String getSscNo() {
		return sscNo;
	}

	public void setSscNo(String sscNo) {
		this.sscNo = sscNo;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
