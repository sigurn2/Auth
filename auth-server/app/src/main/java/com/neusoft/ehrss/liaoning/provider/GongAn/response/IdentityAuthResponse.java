package com.neusoft.ehrss.liaoning.provider.GongAn.response;

import com.neusoft.ehrss.liaoning.provider.GongAn.response.content.IdentityAuthContent;

public class IdentityAuthResponse extends BaseResponse {

	private IdentityAuthContent content;

	public IdentityAuthContent getContent() {
		return content;
	}

	public void setContent(IdentityAuthContent content) {
		this.content = content;
	}

}
