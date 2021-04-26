package com.neusoft.ehrss.liaoning.security.implicit.alipay.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL) 
public class BizContent {

	private String code;

	@JsonProperty("out_sign_no")
	private String signNo;

	@JsonProperty("out_channel")
	private String channelNo;

	@JsonProperty("out_busi_seq")
	private String busiSeq;

	private String idNumber;
	private String name;

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSignNo() {
		return signNo;
	}

	public void setSignNo(String signNo) {
		this.signNo = signNo;
	}

	public String getChannelNo() {
		return channelNo;
	}

	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}

	public String getBusiSeq() {
		return busiSeq;
	}

	public void setBusiSeq(String busiSeq) {
		this.busiSeq = busiSeq;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
