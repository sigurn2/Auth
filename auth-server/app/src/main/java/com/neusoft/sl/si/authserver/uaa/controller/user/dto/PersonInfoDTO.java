package com.neusoft.sl.si.authserver.uaa.controller.user.dto;

import java.io.Serializable;

/**
 * 
 * @author mojf
 *
 */
public class PersonInfoDTO implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/*** 个人Id */
	private Long personId;
	/*** 个人编号 */
	private String personNumber;
	/** 基本信息有效状态 **/
	private String profileStatus;
	/**
	 * 离退休状态
	 */
	private String retireStatus;
	/**
	 * 社保卡号
	 */
	private String socialSecurityCardNumber;

	public String getPersonNumber() {
		return personNumber;
	}

	public void setPersonNumber(String personNumber) {
		this.personNumber = personNumber;
	}

	public String getProfileStatus() {
		return profileStatus;
	}

	public void setProfileStatus(String profileStatus) {
		this.profileStatus = profileStatus;
	}

	public String getRetireStatus() {
		return retireStatus;
	}

	public void setRetireStatus(String retireStatus) {
		this.retireStatus = retireStatus;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public String getSocialSecurityCardNumber() {
		return socialSecurityCardNumber;
	}

	public void setSocialSecurityCardNumber(String socialSecurityCardNumber) {
		this.socialSecurityCardNumber = socialSecurityCardNumber;
	}

}
