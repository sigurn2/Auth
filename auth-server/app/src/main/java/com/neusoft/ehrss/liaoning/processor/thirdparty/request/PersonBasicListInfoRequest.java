package com.neusoft.ehrss.liaoning.processor.thirdparty.request;

import com.neusoft.ehrss.liaoning.processor.thirdparty.dto.cz.PersonBasicInfoInputListDTO;
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
public class PersonBasicListInfoRequest extends AbstractRequest {
    private PersonBasicInfoInputListDTO body;

    public PersonBasicListInfoRequest() {
        super();
    }

    public PersonBasicListInfoRequest(PersonBasicInfoInputListDTO body) {
        super();
        // 创建报文头
        this.crtRequestHead(BuzzNumberEnum.职工人员基本信息查询无过滤.toString(), "1.0",
                LongDateUtils.toSecondLong(new Date()));
        this.body = body;
    }

    @XmlElement(name = "body")
    public PersonBasicInfoInputListDTO getBody() {
        return body;
    }

    public void setBody(PersonBasicInfoInputListDTO body) {
        this.body = body;
    }
}
