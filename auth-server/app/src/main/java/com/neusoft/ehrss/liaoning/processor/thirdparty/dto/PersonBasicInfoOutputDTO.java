package com.neusoft.ehrss.liaoning.processor.thirdparty.dto;

import com.neusoft.sl.si.authserver.base.domains.person.Person;
import com.neusoft.sl.si.authserver.base.domains.person.PersonBasicInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * User: tianmx
 * Date: 2019/12/25
 * Description: 个人基本信息DTO
 */
@ApiModel("人员基本信息")
@XmlRootElement
public class PersonBasicInfoOutputDTO {

    private List<PersonBasicInfo> personBasicInfoList;

    public PersonBasicInfoOutputDTO() {
    }

    public PersonBasicInfoOutputDTO(List<PersonBasicInfo> personBasicInfoList) {
        this.personBasicInfoList = personBasicInfoList;
    }

    @XmlElementWrapper(name = "payments")
    @XmlElement(name = "payment")
    public List<PersonBasicInfo> getPersonBasicInfoList() {
        return personBasicInfoList;
    }
    public void setPersonBasicInfoList(List<PersonBasicInfo> personBasicInfoList) {
        this.personBasicInfoList = personBasicInfoList;
    }


}
