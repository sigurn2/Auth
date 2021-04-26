package com.neusoft.sl.si.authserver.uaa.controller.user.dto;

public class SmsCaptchaDTO {

	private String mobilenumber;

	private String captcha;

	public String getMobilenumber() {
		return mobilenumber;
	}

	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public SmsCaptchaDTO(String mobilenumber, String captcha) {
		super();
		this.mobilenumber = mobilenumber;
		this.captcha = captcha;
	}

}
