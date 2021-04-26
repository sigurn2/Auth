package com.neusoft.ehrss.liaoning.processor.thirdparty.response;

import com.neusoft.ehrss.liaoning.processor.thirdparty.dto.PersonBasicInfoOutputDTO;
import com.neusoft.sl.si.yardman.server.message.AbstractRequest;
import com.neusoft.sl.si.yardman.server.message.AbstractResponse;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created with IntelliJ IDEA.
 * User: tianmx
 * Date: 2019/8/8
 * Time: 11:02
 * Description: 个人基本信息查询返回报文
 */
@XmlRootElement(name = "response")
public class PersonCxBasicInfoResponse extends AbstractResponse<PersonBasicInfoOutputDTO> {

    private PersonBasicInfoOutputDTO body;

    public PersonCxBasicInfoResponse() {
        super();
    }

    /**
     * 反馈错误信息构造
     *
     * @param request
     * @param errorMsg
     */
    public PersonCxBasicInfoResponse(AbstractRequest request, String errorMsg) {
        super(request, errorMsg);
    }

    /**
     * 反馈正确信息构造
     *
     * @param request
     * @param responseBody
     */
    public PersonCxBasicInfoResponse(AbstractRequest request, PersonBasicInfoOutputDTO responseBody) {
        super(request, responseBody);
    }

    @Override
    public PersonBasicInfoOutputDTO getBody() {
        return body;
    }

    @Override
    public void setBody(PersonBasicInfoOutputDTO body) {
        this.body = body;
    }
}
