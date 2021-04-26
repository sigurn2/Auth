package com.neusoft.ehrss.liaoning.processor.thirdparty.response;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.Validate;

import com.neusoft.ehrss.liaoning.processor.thirdparty.dto.TPResultDTO;
import com.neusoft.sl.si.yardman.server.message.AbstractRequest;
import com.neusoft.sl.si.yardman.server.message.AbstractResponse;
import com.neusoft.sl.si.yardman.server.message.ResponseHead;

@XmlRootElement(name = "response")
public class TPResultReponse extends AbstractResponse<TPResultDTO> {

	private TPResultDTO body;

	/**
	 * 继承父类构造函数
	 */
	public TPResultReponse() {
		super();
	}

	/**
	 * 继承父类构造函数
	 *
	 * @param request
	 * @param errorMsg
	 */
	public TPResultReponse(AbstractRequest request, String errorMsg) {
		super(request, errorMsg);
	}

	public TPResultReponse(AbstractRequest request, TPResultDTO responseBody, String errorMsg) {
		Validate.notNull(request, "交易请求不能为空");
		setHead(new ResponseHead(request.getHead(), errorMsg));
		setBody(responseBody);
	}

	/**
	 * 继承父类构造函数
	 *
	 * @param request
	 * @param responseBody
	 */
	public TPResultReponse(AbstractRequest request, TPResultDTO responseBody) {
		super(request, responseBody);
	}

	@Override
	public TPResultDTO getBody() {
		return body;
	}

	@Override
	public void setBody(TPResultDTO body) {
		this.body = body;
	}

}
