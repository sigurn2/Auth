package com.neusoft.ehrss.liaoning.security.implicit.alipay.controller.dto;

public abstract class BaseParams {

	private String clientId;
	private String noncestr;
	private String sign;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getNoncestr() {
		return noncestr;
	}

	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

}
