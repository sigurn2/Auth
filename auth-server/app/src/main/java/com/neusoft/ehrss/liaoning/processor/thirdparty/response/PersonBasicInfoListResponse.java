package com.neusoft.ehrss.liaoning.processor.thirdparty.response;

import com.neusoft.ehrss.liaoning.processor.thirdparty.dto.cz.PersonalEmpListOutResponseDTO;
import com.neusoft.sl.si.yardman.server.message.AbstractRequest;
import com.neusoft.sl.si.yardman.server.message.AbstractResponse;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Description: 职工人员基本信息查询 响应
 * Author: li.jinpeng
 * CreateTime: 2020/3/7
 */
@XmlRootElement(name = "response")
public class PersonBasicInfoListResponse extends AbstractResponse<PersonalEmpListOutResponseDTO> {
    private PersonalEmpListOutResponseDTO body;

    public PersonBasicInfoListResponse(AbstractRequest request, String errorMsg) {
        super(request, errorMsg);
    }

    public PersonBasicInfoListResponse() {
        super();
    }

    public PersonBasicInfoListResponse(AbstractRequest request, PersonalEmpListOutResponseDTO responseBody) {
        super(request, responseBody);
    }

    public PersonBasicInfoListResponse(PersonalEmpListOutResponseDTO body) {
        this.body = body;
    }

    @Override
    public PersonalEmpListOutResponseDTO getBody() {
        return body;
    }

    public void setBody(PersonalEmpListOutResponseDTO body) {
        this.body = body;
    }
}
