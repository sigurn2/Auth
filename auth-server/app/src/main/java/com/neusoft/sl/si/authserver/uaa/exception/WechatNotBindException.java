package com.neusoft.sl.si.authserver.uaa.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 异常处理
 * 
 * @author zhou.haidong
 * @version 2017-10-31
 */
public class WechatNotBindException extends AuthenticationException {
	private static final long serialVersionUID = 1L;

	private String openid;

	public WechatNotBindException(String msg, String openid) {
		super(msg);
		this.openid = openid;
	}

	public String getOpenid() {
		return openid;
	}

}
