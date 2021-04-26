package com.neusoft.sl.si.authserver.uaa.controller.user.dto.rlzysc;

/**
 * 
 * @author mojf
 *
 */
public class PersonOpenDTO extends OpenUserDTO {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 证件号码
	 */
	private String idNumber;

	/**
	 * 手机号码
	 */
	private String mobile;

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile
	 *            the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

}
