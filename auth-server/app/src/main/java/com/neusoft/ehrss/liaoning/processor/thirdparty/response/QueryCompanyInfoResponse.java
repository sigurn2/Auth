package com.neusoft.ehrss.liaoning.processor.thirdparty.response;

import com.neusoft.ehrss.liaoning.processor.thirdparty.dto.UnitInfoOutputDTO;
import com.neusoft.sl.si.yardman.server.message.AbstractRequest;
import com.neusoft.sl.si.yardman.server.message.AbstractResponse;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 单位基本信息查询--返回
 * 
 * @author tianmx
 *
 */
@XmlRootElement(name = "response")
public class QueryCompanyInfoResponse extends AbstractResponse<UnitInfoOutputDTO> {

    private UnitInfoOutputDTO body;

    /**
     * 继承父类构造函数
     */
    public QueryCompanyInfoResponse() {
        super();
    }

    /**
     * 继承父类构造函数
     *
     * @param request
     * @param errorMsg
     */
    public QueryCompanyInfoResponse(AbstractRequest request, String errorMsg) {
        super(request, errorMsg);
    }

    /**
     * 继承父类构造函数
     *
     * @param request
     * @param responseBody
     */
    public QueryCompanyInfoResponse(AbstractRequest request, UnitInfoOutputDTO responseBody) {
        super(request, responseBody);
    }

    /**
     * @return the body
     */
    @Override
    public UnitInfoOutputDTO getBody() {
        return body;
    }

    /**
     * @param body the body to set
     */
    public void setBody(UnitInfoOutputDTO body) {
        this.body = body;
    }

}