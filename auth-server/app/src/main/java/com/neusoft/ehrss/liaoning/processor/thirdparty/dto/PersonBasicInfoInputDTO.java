package com.neusoft.ehrss.liaoning.processor.thirdparty.dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 城居人员基础信息修改查询（入参）
 * @author tianmx
 * @time 20191223
 */
@XmlRootElement
public class PersonBasicInfoInputDTO {

    /**
     * 姓名
     */
    private String aac003;
    /**
     * 证件类型
     */
    private String aac058;
    /**
     * 证件号码
     */
    private String aac002;

    public PersonBasicInfoInputDTO() {
    }


    public PersonBasicInfoInputDTO(String aac003, String aac058, String aac002) {
        this.aac003 = aac003;
        this.aac058 = aac058;
        this.aac002 = aac002;
    }

    public String getAac003() {
        return aac003;
    }

    public void setAac003(String aac003) {
        this.aac003 = aac003;
    }

    public String getAac058() {
        return aac058;
    }

    public void setAac058(String aac058) {
        this.aac058 = aac058;
    }

    public String getAac002() {
        return aac002;
    }

    public void setAac002(String aac002) {
        this.aac002 = aac002;
    }
}
