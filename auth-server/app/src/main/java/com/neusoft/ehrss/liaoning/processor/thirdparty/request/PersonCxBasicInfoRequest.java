package com.neusoft.ehrss.liaoning.processor.thirdparty.request;

import com.neusoft.ehrss.liaoning.processor.thirdparty.dto.PersonBasicInfoInputDTO;
import com.neusoft.sl.girder.utils.LongDateUtils;
import com.neusoft.sl.si.authserver.simis.BuzzNumberEnum;
import com.neusoft.sl.si.yardman.server.message.AbstractRequest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: tianmx
 * Date: 2019/12/25
 * Time: 11:02
 * Description: 个人基本信息查询请求报文
 */
@XmlRootElement(name = "request")
public class PersonCxBasicInfoRequest extends AbstractRequest {

    private PersonBasicInfoInputDTO body;



    public PersonCxBasicInfoRequest() {
        super();
    }

    public PersonCxBasicInfoRequest(PersonBasicInfoInputDTO body) {
        super();
        // 创建报文头
        this.crtRequestHead(BuzzNumberEnum.城居人员基础信息查询.toString(), "1.0",
                LongDateUtils.toSecondLong(new Date()));
        this.body = body;
    }

    @XmlElement(name = "body")
    public PersonBasicInfoInputDTO getBody() {
        return body;
    }

    public void setBody(PersonBasicInfoInputDTO body) {
        this.body = body;
    }
}
