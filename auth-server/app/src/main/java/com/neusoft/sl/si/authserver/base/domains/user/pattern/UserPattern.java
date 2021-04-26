package com.neusoft.sl.si.authserver.base.domains.user.pattern;

/**
 * T_USER_PATTERN 实体类
 * @author zhou.haidong
 */
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(UserPatternId.class)
@Access(AccessType.FIELD)
@Table(name = "T_USER_PATTERN")
public class UserPattern {

	/**
	 * 设备唯一标识
	 */
	@Id
	@Column(name = "DEVICE_ID")
	private String deviceId;

	/**
	 * 用户ID
	 */
	@Id
	@Column(name = "USER_ID")
	private String userId;

	/**
	 * 设置的APP的类别
	 */
	@Id
	@Column(name = "APP_TYPE")
	private String appType;

	/**
	 * 手势解锁密码
	 */
	@Column(name = "PATTERN_PWD")
	private String patternPwd;

	/**
	 * 是否启用。1-有效，0-无效
	 */
	@Column
	private boolean available;

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

	public String getPatternPwd() {
		return patternPwd;
	}

	public void setPatternPwd(String patternPwd) {
		this.patternPwd = patternPwd;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

}
