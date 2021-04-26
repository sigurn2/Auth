package com.neusoft.ehrss.liaoning.processor.thirdparty.request;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.neusoft.ehrss.liaoning.processor.thirdparty.dto.TPQueryPersonQueryDTO;
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
public class TPQueryPersonRequest extends AbstractRequest {

	private TPQueryPersonQueryDTO tpQueryPersonQueryDTO;

	/**
	 * 默认构造函数
	 */
	public TPQueryPersonRequest() {
		super();
	}

	/**
	 * 按身份证号查询
	 * 
	 * @param idNumber
	 */
	public TPQueryPersonRequest(TPQueryPersonQueryDTO tpQueryPersonQueryDTO) {
		super();
		// 创建报文头
		this.crtRequestHead(BuzzNumberEnum.第三方个人用户信息查询.toString(), ApiVersion.SERVER_VERSION, LongDateUtils.toSecondLong(new Date()));
		// 设置查询参数
		this.tpQueryPersonQueryDTO = tpQueryPersonQueryDTO;
	}

	/**
	 * @return the queryEmployeePaymentDTO
	 */
	@XmlElement(name = "body")
	public TPQueryPersonQueryDTO getTPQueryPersonQueryDTO() {
		return tpQueryPersonQueryDTO;
	}

	public void setTPQueryPersonQueryDTO(TPQueryPersonQueryDTO tpQueryPersonQueryDTO) {
		this.tpQueryPersonQueryDTO = tpQueryPersonQueryDTO;
	}

}
