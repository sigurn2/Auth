package com.neusoft.ehrss.liaoning.security.password.mobile.person;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class RlzyscPersonAuthenticationToken extends UsernamePasswordAuthenticationToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RlzyscPersonAuthenticationToken(Object principal, Object credentials) {
		super(principal, credentials);
	}

}
