package com.neusoft.ehrss.liaoning.processor.thirdparty.request;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.neusoft.ehrss.liaoning.processor.thirdparty.dto.TPQueryPersonSiInfoQueryDTO;
import com.neusoft.sl.si.authserver.simis.ApiVersion;
import com.neusoft.sl.si.authserver.simis.BuzzNumberEnum;
import com.neusoft.sl.si.yardman.server.message.AbstractRequest;
import com.neusoft.sl.si.yardman.server.utils.LongDateUtils;

/**
 * 查询用户信息交易报文请求
 * 
 * @author mojf
 *
 */

@XmlRootElement(name = "request")
public class TPQueryPersonSiInfoRequest extends AbstractRequest {

	private TPQueryPersonSiInfoQueryDTO tpQueryPersonSiInfoQueryDTO;

	/**
	 * 默认构造函数
	 */
	public TPQueryPersonSiInfoRequest() {
		super();
	}

	/**
	 * 按身份证号姓名查询
	 * 
	 * @param idNumber
	 */
	public TPQueryPersonSiInfoRequest(TPQueryPersonSiInfoQueryDTO tpQueryPersonSiInfoQueryDTO) {
		super();
		// 创建报文头
		this.crtRequestHead(BuzzNumberEnum.第三方个人用户社保信息查询.toString(), ApiVersion.SERVER_VERSION, LongDateUtils.toSecondLong(new Date()));
		// 设置查询参数
		this.setTpQueryPersonSiInfoQueryDTO(tpQueryPersonSiInfoQueryDTO);
	}

	@XmlElement(name = "body")
	public TPQueryPersonSiInfoQueryDTO getTpQueryPersonSiInfoQueryDTO() {
		return tpQueryPersonSiInfoQueryDTO;
	}

	public void setTpQueryPersonSiInfoQueryDTO(TPQueryPersonSiInfoQueryDTO tpQueryPersonSiInfoQueryDTO) {
		this.tpQueryPersonSiInfoQueryDTO = tpQueryPersonSiInfoQueryDTO;
	}

}
