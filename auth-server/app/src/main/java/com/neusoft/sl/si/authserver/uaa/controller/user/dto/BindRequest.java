package com.neusoft.sl.si.authserver.uaa.controller.user.dto;

import com.neusoft.ehrss.liaoning.processor.thirdparty.dto.TPChangePasswordUserDTO;
import com.neusoft.sl.si.authserver.simis.ApiVersion;
import com.neusoft.sl.si.authserver.simis.BuzzNumberEnum;
import com.neusoft.sl.si.yardman.server.message.AbstractRequest;
import com.neusoft.sl.si.yardman.server.utils.LongDateUtils;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * @program: liaoning-auth
 * @description:
 * @author: lgy
 * @create: 2020-03-10 08:55
 **/
@XmlRootElement(name = "request")
public class BindRequest  extends AbstractRequest {

    private BindRequestDTO bindRequestDTO;

    /**
     * 默认构造器
     */
    public BindRequest() {
        super();
        this.crtRequestHead(BuzzNumberEnum.单位绑定接口.toString(), ApiVersion.SERVER_VERSION,
                LongDateUtils.toSecondLong(new Date()));
    }

    public BindRequest(BindRequestDTO bindRequestDTO, String transitionId, String operatorName) {
        super();
        // 创建报文头
        this.crtRequestHead(BuzzNumberEnum.单位绑定接口.toString(), ApiVersion.SERVER_VERSION, transitionId, operatorName,
                LongDateUtils.toSecondLong(new Date()));
        this.setBindRequestDTO(bindRequestDTO);
    }

    @XmlElement(name = "body")
    public BindRequestDTO getBindRequestDTO() {
        return bindRequestDTO;
    }

    public void setBindRequestDTO(BindRequestDTO bindRequestDTO) {
        this.bindRequestDTO = bindRequestDTO;
    }
}
