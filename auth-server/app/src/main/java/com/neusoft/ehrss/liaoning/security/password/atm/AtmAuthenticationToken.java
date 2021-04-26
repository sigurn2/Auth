package com.neusoft.ehrss.liaoning.security.password.atm;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class AtmAuthenticationToken extends UsernamePasswordAuthenticationToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AtmAuthenticationToken(Object principal, Object credentials) {
		super(principal, credentials);
	}

}
