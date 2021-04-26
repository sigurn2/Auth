package com.neusoft.ehrss.liaoning.security.password.mobile.company;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class RlzyscCompanyAuthenticationToken extends UsernamePasswordAuthenticationToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RlzyscCompanyAuthenticationToken(Object principal, Object credentials) {
		super(principal, credentials);
	}

}
