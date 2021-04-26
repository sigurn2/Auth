package com.neusoft.ehrss.liaoning.processor.thirdparty.request;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.neusoft.ehrss.liaoning.processor.thirdparty.dto.TPResetSensitiveInfoDTO;
import com.neusoft.sl.si.authserver.simis.ApiVersion;
import com.neusoft.sl.si.authserver.simis.BuzzNumberEnum;
import com.neusoft.sl.si.yardman.server.message.AbstractRequest;
import com.neusoft.sl.si.yardman.server.utils.LongDateUtils;
/**
 * 第三方密码修改
 * @author ZHOUHD
 *
 */
@XmlRootElement(name = "request")
public class TPResetSensitiveInfoRequest extends AbstractRequest {
	
	private TPResetSensitiveInfoDTO tpResetSensitiveInfoDTO;

    /**
     * 默认构造器
     */
    public TPResetSensitiveInfoRequest() {
        super();
        this.crtRequestHead(BuzzNumberEnum.第三方敏感信息重置.toString(), ApiVersion.SERVER_VERSION,
                LongDateUtils.toSecondLong(new Date()));
    }

    public TPResetSensitiveInfoRequest(TPResetSensitiveInfoDTO tpResetSensitiveInfoDTO, String transitionId, String operatorName) {
        super();
        // 创建报文头
        this.crtRequestHead(BuzzNumberEnum.第三方敏感信息重置.toString(), ApiVersion.SERVER_VERSION, transitionId, operatorName,
                LongDateUtils.toSecondLong(new Date()));
        this.setTpResetSensitiveInfoDTO(tpResetSensitiveInfoDTO);
    }

	@XmlElement(name = "body")
	public TPResetSensitiveInfoDTO getTpResetSensitiveInfoDTO() {
		return tpResetSensitiveInfoDTO;
	}

	public void setTpResetSensitiveInfoDTO(TPResetSensitiveInfoDTO tpResetSensitiveInfoDTO) {
		this.tpResetSensitiveInfoDTO = tpResetSensitiveInfoDTO;
	}

}
