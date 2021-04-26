package com.neusoft.ehrss.liaoning.security.implicit.alipay.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthorizeResponse extends BaseParams {

	@JsonProperty("biz_content")
	private BizContent bizContent;

	private ResultInfo resultInfo;

	public BizContent getBizContent() {
		return bizContent;
	}

	public void setBizContent(BizContent bizContent) {
		this.bizContent = bizContent;
	}

	public ResultInfo getResultInfo() {
		return resultInfo;
	}

	public void setResultInfo(ResultInfo resultInfo) {
		this.resultInfo = resultInfo;
	}

}
