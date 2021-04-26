package com.neusoft.sl.si.authserver.simis.support.gateway.dto;

public class WT1004ResultDTO {

	/*
	 * 是否可以经办该业务的标志
	 */
	private String appcode;

	/*
	 * 不可以经办的原因
	 */
	private String appmsg;

	public String getAppcode() {
		return appcode;
	}

	public String getAppmsg() {
		return appmsg;
	}

	public void setAppcode(String appcode) {
		this.appcode = appcode;
	}

	public void setAppmsg(String appmsg) {
		this.appmsg = appmsg;
	}

	public WT1004ResultDTO(String appcode, String appmsg) {
		super();
		this.appcode = appcode;
		this.appmsg = appmsg;
	}

	public WT1004ResultDTO() {
		super();
	}

}
