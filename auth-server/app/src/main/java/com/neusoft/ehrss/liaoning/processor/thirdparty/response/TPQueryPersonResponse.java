package com.neusoft.ehrss.liaoning.processor.thirdparty.response;

import javax.xml.bind.annotation.XmlRootElement;

import com.neusoft.ehrss.liaoning.processor.thirdparty.dto.TPQueryPersonDTO;
import com.neusoft.sl.si.yardman.server.message.AbstractRequest;
import com.neusoft.sl.si.yardman.server.message.AbstractResponse;

@XmlRootElement(name = "response")
public class TPQueryPersonResponse extends AbstractResponse<TPQueryPersonDTO> {

	public TPQueryPersonDTO getBody() {
		return body;
	}

	public void setBody(TPQueryPersonDTO body) {
		this.body = body;
	}

	private TPQueryPersonDTO body;

	/**
	 * 继承父类构造函数
	 */
	public TPQueryPersonResponse() {
		super();
	}

	/**
	 * 继承父类构造函数
	 *
	 * @param request
	 * @param errorMsg
	 */
	public TPQueryPersonResponse(AbstractRequest request, String errorMsg) {
		super(request, errorMsg);
	}

	/**
	 * 继承父类构造函数
	 *
	 * @param request
	 * @param responseBody
	 */
	public TPQueryPersonResponse(AbstractRequest request, TPQueryPersonDTO responseBody) {
		super(request, responseBody);
	}

}
