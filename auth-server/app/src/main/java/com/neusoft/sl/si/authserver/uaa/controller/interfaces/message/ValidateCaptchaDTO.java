package com.neusoft.sl.si.authserver.uaa.controller.interfaces.message;

public class ValidateCaptchaDTO {

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

}
