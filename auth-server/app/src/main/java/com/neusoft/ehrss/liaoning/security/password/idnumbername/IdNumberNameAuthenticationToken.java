package com.neusoft.ehrss.liaoning.security.password.idnumbername;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class IdNumberNameAuthenticationToken extends UsernamePasswordAuthenticationToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IdNumberNameAuthenticationToken(Object principal, Object credentials) {
		super(principal, credentials);
	}

}
