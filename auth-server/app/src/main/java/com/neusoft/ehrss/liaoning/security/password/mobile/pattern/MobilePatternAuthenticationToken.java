package com.neusoft.ehrss.liaoning.security.password.mobile.pattern;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class MobilePatternAuthenticationToken extends UsernamePasswordAuthenticationToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 设备唯一标识
	private String deviceId;
	// APP类型 1-人社
	private String appType;

	public MobilePatternAuthenticationToken(Object principal, Object credentials, String deviceId, String appType) {
		super(principal, credentials);
		this.deviceId = deviceId;
		this.appType = appType;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

}
