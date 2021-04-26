package com.neusoft.ehrss.liaoning.processor.thirdparty.request;

import com.neusoft.ehrss.liaoning.processor.thirdparty.dto.UnitInfoInputDTO;
import com.neusoft.sl.si.authserver.simis.ApiVersion;
import com.neusoft.sl.si.authserver.simis.BuzzNumberEnum;
import com.neusoft.sl.si.yardman.server.message.AbstractRequest;
import com.neusoft.sl.si.yardman.server.message.RequestHead;
import com.neusoft.sl.si.yardman.server.utils.LongDateUtils;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * 请求--单位及参保信息查询
 * 
 * @author tianmx
 *
 */
@XmlRootElement(name = "request")
public class QueryCompanyInfoRequest extends AbstractRequest {

	private UnitInfoInputDTO dto;

	/**
	 * 默认构造函数
	 */
	public QueryCompanyInfoRequest() {
		super();
	}

	public QueryCompanyInfoRequest(UnitInfoInputDTO dto) {
		super();
		// 创建报文头
		this.crtRequestHead(BuzzNumberEnum.单位基本信息查询.toString(), ApiVersion.SERVER_VERSION,
				LongDateUtils.toSecondLong(new Date()));
		RequestHead requestHead = this.getHead();
		requestHead.setSender("NEUSOFT_GGFW_WEB");
		this.setHead(requestHead);
		this.dto = dto;
	}

	@Override
	protected void crtRequestHead(String buzzNumber, String version, String transitionId, String operatorName, Long datetime) {
		super.crtRequestHead(buzzNumber, version, transitionId, operatorName, datetime);
	}

	/**
	 * @return the dto
	 */
	@XmlElement(name = "body")
	public UnitInfoInputDTO getDto() {
		return dto;
	}

	/**
	 * @param dto
	 *            the dto to set
	 */
	public void setDto(UnitInfoInputDTO dto) {
		this.dto = dto;
	}
}
