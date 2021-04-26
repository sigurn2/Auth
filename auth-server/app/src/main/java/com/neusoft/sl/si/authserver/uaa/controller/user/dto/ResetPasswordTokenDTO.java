package com.neusoft.sl.si.authserver.uaa.controller.user.dto;

import java.io.Serializable;

public class ResetPasswordTokenDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public ResetPasswordTokenDTO(String token) {
		super();
		this.token = token;
	}

}
