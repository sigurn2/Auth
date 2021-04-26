package com.neusoft.ehrss.liaoning.processor.thirdparty.request;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.neusoft.sl.si.authserver.simis.ApiVersion;
import com.neusoft.sl.si.authserver.simis.BuzzNumberEnum;
import com.neusoft.sl.si.authserver.uaa.controller.interfaces.user.dto.PersonUserDTO;
import com.neusoft.sl.si.yardman.server.message.AbstractRequest;
import com.neusoft.sl.si.yardman.server.utils.LongDateUtils;

@XmlRootElement(name = "request")
public class TPPersonRegisterRequest extends AbstractRequest {

	private PersonUserDTO personUserDTO;

	public TPPersonRegisterRequest() {
		super();
		// 创建报文头
		this.crtRequestHead(BuzzNumberEnum.第三方个人账号注册.toString(), ApiVersion.SERVER_VERSION, LongDateUtils.toSecondLong(new Date()));
	}

	public TPPersonRegisterRequest(PersonUserDTO personUserDTO, String transitionId, String operatorName) {
		super();
		// 创建报文头
		this.crtRequestHead(BuzzNumberEnum.第三方个人账号注册.toString(), ApiVersion.SERVER_VERSION, transitionId, operatorName, LongDateUtils.toSecondLong(new Date()));
		this.personUserDTO = personUserDTO;
	}

	@XmlElement(name = "body")
	public PersonUserDTO getPersonUserDTO() {
		return personUserDTO;
	}

	public void setPersonUserDTO(PersonUserDTO personUserDTO) {
		this.personUserDTO = personUserDTO;
	}

}
