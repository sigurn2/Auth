package com.neusoft.ehrss.liaoning.security.password.ecard;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class EcardGatewayAuthenticationToken extends UsernamePasswordAuthenticationToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EcardGatewayAuthenticationToken(Object principal, Object credentials) {
		super(principal, credentials);
	}

}
