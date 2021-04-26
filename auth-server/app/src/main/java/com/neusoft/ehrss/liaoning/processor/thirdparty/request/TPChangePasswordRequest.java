package com.neusoft.ehrss.liaoning.processor.thirdparty.request;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.neusoft.ehrss.liaoning.processor.thirdparty.dto.TPChangePasswordUserDTO;
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
public class TPChangePasswordRequest extends AbstractRequest {
	
	private TPChangePasswordUserDTO tpChangePasswordUserDTO;

    /**
     * 默认构造器
     */
    public TPChangePasswordRequest() {
        super();
        this.crtRequestHead(BuzzNumberEnum.第三方密码修改.toString(), ApiVersion.SERVER_VERSION,
                LongDateUtils.toSecondLong(new Date()));
    }

    public TPChangePasswordRequest(TPChangePasswordUserDTO tpChangePasswordUserDTO, String transitionId, String operatorName) {
        super();
        // 创建报文头
        this.crtRequestHead(BuzzNumberEnum.第三方密码修改.toString(), ApiVersion.SERVER_VERSION, transitionId, operatorName,
                LongDateUtils.toSecondLong(new Date()));
        this.setTpChangePasswordUserDTO(tpChangePasswordUserDTO);
    }

    @XmlElement(name = "body")
	public TPChangePasswordUserDTO getTpChangePasswordUserDTO() {
		return tpChangePasswordUserDTO;
	}

	public void setTpChangePasswordUserDTO(TPChangePasswordUserDTO tpChangePasswordUserDTO) {
		this.tpChangePasswordUserDTO = tpChangePasswordUserDTO;
	}

}
