package com.neusoft.ehrss.liaoning.security.password.lst;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class LstAuthenticationToken extends UsernamePasswordAuthenticationToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LstAuthenticationToken(Object principal, Object credentials) {
		super(principal, credentials);
	}

}
