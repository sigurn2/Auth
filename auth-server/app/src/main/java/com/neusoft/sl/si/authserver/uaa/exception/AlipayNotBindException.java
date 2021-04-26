package com.neusoft.sl.si.authserver.uaa.exception;

import org.springframework.security.core.AuthenticationException;

public class AlipayNotBindException extends AuthenticationException {
	private static final long serialVersionUID = 1L;

	private String pseudoOpenid;

	public AlipayNotBindException(String msg, String pseudoOpenid) {
		super(msg);
		this.pseudoOpenid = pseudoOpenid;
	}

	public String getPseudoOpenid() {
		return pseudoOpenid;
	}
}
