package com.neusoft.ehrss.liaoning.security.implicit.alipay.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthorizeRequest extends BaseParams {

	@JsonProperty("biz_content")
	private BizContent bizContent;

	public BizContent getBizContent() {
		return bizContent;
	}

	public void setBizContent(BizContent bizContent) {
		this.bizContent = bizContent;
	}

}
