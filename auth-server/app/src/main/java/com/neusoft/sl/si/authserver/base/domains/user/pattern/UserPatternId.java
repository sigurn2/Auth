package com.neusoft.sl.si.authserver.base.domains.user.pattern;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * T_USER_PATTERN 联合主键
 * 
 * @author zhou.haidong
 *
 */
@Embeddable
public class UserPatternId implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private String userId;
	private String deviceId;
	private String appType;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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
