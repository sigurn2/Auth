package com.neusoft.ehrss.liaoning.pattern.controller.dto;

public class UserPatternDTO {

	private String deviceId;
	private String patternPwd;
	private String appType;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getPatternPwd() {
		return patternPwd;
	}

	public void setPatternPwd(String patternPwd) {
		this.patternPwd = patternPwd;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

}
