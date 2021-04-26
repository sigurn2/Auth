package com.neusoft.ehrss.liaoning.processor.thirdparty.dto.cz;

import io.swagger.annotations.ApiModel;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: tianmx9
 * Date: 2020/01/03
 * Time: 11:02
 * Description: 调用中心报表返回DTO
 */
@ApiModel("职工人员基本信息查询(无过滤)")
@XmlRootElement
public class PersonalEmpListOutResponseDTO {

    /** 返回值实际结果 */
    private List<PersonBasicInfoCZDTO> list;

    @XmlElementWrapper(name = "personList")
    @XmlElement(name = "person")
    public List<PersonBasicInfoCZDTO> getList() {
        return list;
    }

    public void setList(List<PersonBasicInfoCZDTO> list) {
        this.list = list;
    }

}
