package com.neusoft.ehrss.liaoning.processor.thirdparty.request;

import com.neusoft.ehrss.liaoning.processor.thirdparty.dto.cz.PersonBasicInfoInputCZDTO;
import com.neusoft.sl.girder.utils.LongDateUtils;
import com.neusoft.sl.si.authserver.simis.BuzzNumberEnum;
import com.neusoft.sl.si.yardman.server.message.AbstractRequest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Description: 职工人员基本信息查询 请求
 * Author: li.jinpeng
 * CreateTime: 2020/3/7
 */
@XmlRootElement(name = "request")
public class PersonBasicInfoCZRequest extends AbstractRequest {
    private PersonBasicInfoInputCZDTO body;

    public PersonBasicInfoCZRequest() {
        super();
    }

    public PersonBasicInfoCZRequest(PersonBasicInfoInputCZDTO body) {
        super();
        // 创建报文头
        this.crtRequestHead(BuzzNumberEnum.职工人员基本信息查询.toString(),"1.0",
                LongDateUtils.toSecondLong(new Date()));
        this.body = body;
    }

    @XmlElement(name = "body")
    public PersonBasicInfoInputCZDTO getBody() {
        return body;
    }

    public void setBody(PersonBasicInfoInputCZDTO body) {
        this.body = body;
    }
}
