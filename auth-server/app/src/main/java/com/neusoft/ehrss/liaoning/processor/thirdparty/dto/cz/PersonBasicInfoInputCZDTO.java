package com.neusoft.ehrss.liaoning.processor.thirdparty.dto.cz;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Description: 职工基本信息查询 入参DTO
 * Author: li.jinpeng
 * CreateTime: 2020/3/7
 */
@XmlRootElement
public class PersonBasicInfoInputCZDTO {
    private String socialSecurityNumber; // 社会保障号码
    private String name; // 姓名

    public PersonBasicInfoInputCZDTO(){};

    public PersonBasicInfoInputCZDTO(String socialSecurityNumber, String name) {
        this.socialSecurityNumber = socialSecurityNumber;
        this.name = name;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
