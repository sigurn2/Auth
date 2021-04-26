package com.neusoft.ehrss.liaoning.processor.thirdparty.response;

import com.neusoft.ehrss.liaoning.processor.thirdparty.dto.cz.PersonBasicInfoCZDTO;
import com.neusoft.sl.si.yardman.server.message.AbstractRequest;
import com.neusoft.sl.si.yardman.server.message.AbstractResponse;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Description: 职工人员基本信息查询 响应
 * Author: li.jinpeng
 * CreateTime: 2020/3/7
 */
@XmlRootElement(name = "response")
public class PersonBasicInfoCZResponse extends AbstractResponse<PersonBasicInfoCZDTO> {
    private PersonBasicInfoCZDTO body;

    public PersonBasicInfoCZResponse(AbstractRequest request, String errorMsg) {
        super(request, errorMsg);
    }

    public PersonBasicInfoCZResponse() {
        super();
    }

    public PersonBasicInfoCZResponse(AbstractRequest request, PersonBasicInfoCZDTO responseBody) {
        super(request, responseBody);
    }

    public PersonBasicInfoCZResponse(PersonBasicInfoCZDTO body) {
        this.body = body;
    }

    @Override
    public PersonBasicInfoCZDTO getBody() {
        return body;
    }

    public void setBody(PersonBasicInfoCZDTO body) {
        this.body = body;
    }
}
