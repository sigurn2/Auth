package com.neusoft.ehrss.liaoning.processor.thirdparty.request;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.neusoft.ehrss.liaoning.processor.thirdparty.dto.TPEnterpriseUserDTO;
import com.neusoft.sl.si.authserver.simis.ApiVersion;
import com.neusoft.sl.si.authserver.simis.BuzzNumberEnum;
import com.neusoft.sl.si.yardman.server.message.AbstractRequest;
import com.neusoft.sl.si.yardman.server.utils.LongDateUtils;

/**
 * 第三方企业注册（未参保）
 * 
 * @author ZHOUHD
 *
 */
@XmlRootElement(name = "request")
public class TPCompanyRegisterRequest extends AbstractRequest {

	private TPEnterpriseUserDTO tPEnterpriseUserDTO;

	/**
	 * 默认构造器
	 */
	public TPCompanyRegisterRequest() {
		super();
		this.crtRequestHead(BuzzNumberEnum.第三方企业账号注册.toString(), ApiVersion.SERVER_VERSION, LongDateUtils.toSecondLong(new Date()));
	}

	public TPCompanyRegisterRequest(TPEnterpriseUserDTO tPEnterpriseUserDTO, String transitionId, String operatorName) {
		super();
		// 创建报文头
		this.crtRequestHead(BuzzNumberEnum.第三方企业账号注册.toString(), ApiVersion.SERVER_VERSION, transitionId, operatorName, LongDateUtils.toSecondLong(new Date()));
		this.tPEnterpriseUserDTO = tPEnterpriseUserDTO;
	}

	/**
	 * @return the queryEmployeePaymentDTO
	 */
	@XmlElement(name = "body")
	public TPEnterpriseUserDTO getTPEnterpriseUserDTO() {
		return tPEnterpriseUserDTO;
	}

	public void setTPEnterpriseUserDTO(TPEnterpriseUserDTO tPEnterpriseUserDTO) {
		this.tPEnterpriseUserDTO = tPEnterpriseUserDTO;
	}

}
