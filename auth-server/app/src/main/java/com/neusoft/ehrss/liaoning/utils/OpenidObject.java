package com.neusoft.ehrss.liaoning.utils;

public class OpenidObject {

	private String type;

	private String openid;

	public OpenidObject(String type, String openid) {
		super();
		this.type = type;
		this.openid = openid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

}
