package com.neusoft.ehrss.liaoning.provider.GongAn.response;

import com.neusoft.ehrss.liaoning.provider.GongAn.response.content.GetTimestampContent;

public class GetTimestampResponse extends BaseResponse {
	
	private GetTimestampContent content;

	public GetTimestampContent getContent() {
		return content;
	}

	public void setContent(GetTimestampContent content) {
		this.content = content;
	} 

}
