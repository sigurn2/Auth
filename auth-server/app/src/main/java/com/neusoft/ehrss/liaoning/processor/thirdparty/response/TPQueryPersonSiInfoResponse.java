package com.neusoft.ehrss.liaoning.processor.thirdparty.response;

import javax.xml.bind.annotation.XmlRootElement;

import com.neusoft.ehrss.liaoning.processor.thirdparty.dto.TPQueryPersonSiInfoDTO;
import com.neusoft.sl.si.yardman.server.message.AbstractRequest;
import com.neusoft.sl.si.yardman.server.message.AbstractResponse;

@XmlRootElement(name = "response")
public class TPQueryPersonSiInfoResponse extends AbstractResponse<TPQueryPersonSiInfoDTO> {

	public TPQueryPersonSiInfoDTO getBody() {
		return body;
	}

	public void setBody(TPQueryPersonSiInfoDTO body) {
		this.body = body;
	}

	private TPQueryPersonSiInfoDTO body;

	/**
	 * 继承父类构造函数
	 */
	public TPQueryPersonSiInfoResponse() {
		super();
	}

	/**
	 * 继承父类构造函数
	 *
	 * @param request
	 * @param errorMsg
	 */
	public TPQueryPersonSiInfoResponse(AbstractRequest request, String errorMsg) {
		super(request, errorMsg);
	}

	/**
	 * 继承父类构造函数
	 *
	 * @param request
	 * @param responseBody
	 */
	public TPQueryPersonSiInfoResponse(AbstractRequest request, TPQueryPersonSiInfoDTO responseBody) {
		super(request, responseBody);
	}

}
