package com.neusoft.sl.si.authserver.uaa.controller.user.dto.rlzysc;

import java.io.Serializable;

public class OpenUserDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用户类型，1-企业，2-个人
	 */
	private String userType;

	private String name;

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
