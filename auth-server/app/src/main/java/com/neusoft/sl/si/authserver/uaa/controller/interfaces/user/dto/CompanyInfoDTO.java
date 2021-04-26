package com.neusoft.sl.si.authserver.uaa.controller.interfaces.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "taxCode" })
public class CompanyInfoDTO {

	/** 单位Id */
	private Long id;

	/** 单位编号 */
	private String companyNumber;

	/** 单位名称 */
	private String name;

	/** 统一社会信用代码 **/
	private String orgCode;
	/**
	 * 税号
	 */
	private String taxCode;
	/**
	 * 所在地行政区划代码
	 */
	private String areaCode;

	/**
	 * 是否参保，0-未参保，1-已参保
	 */
	private String isSocial;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCompanyNumber() {
		return companyNumber;
	}

	public void setCompanyNumber(String companyNumber) {
		this.companyNumber = companyNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getIsSocial() {
		return isSocial;
	}

	public void setIsSocial(String isSocial) {
		this.isSocial = isSocial;
	}

}
