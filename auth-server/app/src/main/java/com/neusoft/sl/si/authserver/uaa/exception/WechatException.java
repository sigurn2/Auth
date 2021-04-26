package com.neusoft.sl.si.authserver.uaa.exception;

/**
 * 异常处理
 * @author zhou.haidong
 * @version 2017-10-31
 */
public class WechatException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public WechatException(String msg){
		super(msg);
	}
}
