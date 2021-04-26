
package com.neusoft.sl.si.authserver.uaa.exception;

import org.springframework.security.core.AuthenticationException;

public class AlipayAutException extends AuthenticationException {
	private static final long serialVersionUID = 1L;

	private String pseudoOpenid;

	public AlipayAutException(String msg) {
		super(msg);
	}
}
