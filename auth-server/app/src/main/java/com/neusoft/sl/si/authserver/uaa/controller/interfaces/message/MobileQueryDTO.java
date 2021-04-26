package com.neusoft.sl.si.authserver.uaa.controller.interfaces.message;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author mojf
 *
 */
public class MobileQueryDTO implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8392171484913848621L;
	@ApiModelProperty(value = "手机号码")
	private String mobilenumber;
	@ApiModelProperty(value = "姓名")
	private String name;
	@ApiModelProperty(value = "身份证号码或社保卡号")
	private String idNumber;
	/**
	 * 判断是否修改手机号码 0--不修改 1--修改
	 */
	@ApiModelProperty(value = "是否修改手机号码")
	private String modify;

	private String personNumber;

	private String random;

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

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String getModify() {
		return modify;
	}

	public void setModify(String modify) {
		this.modify = modify;
	}

	public MobileQueryDTO() {
		super();
	}

	public MobileQueryDTO(String encodeNumber) {
		super();
		this.mobilenumber = encodeNumber;
	}

	public String getPersonNumber() {
		return personNumber;
	}

	public void setPersonNumber(String personNumber) {
		this.personNumber = personNumber;
	}

	public String getRandom() {
		return random;
	}

	public void setRandom(String random) {
		this.random = random;
	}

}
