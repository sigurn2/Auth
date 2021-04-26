package com.neusoft.ehrss.liaoning.processor.thirdparty.dto.cz;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Description: 参保信息DTO
 * Author: li.jinpeng
 * CreateTime: 2020/3/7
 */

public class PersonInsuredInfoDTO {

    @ApiModelProperty("单位名称")
    private String unitName; // 单位名称

    @ApiModelProperty("险种类型")
    private String insuranceType; // 险种类型

    @ApiModelProperty("参保状态")
    private String insuredStatus; // 参保状态

    @ApiModelProperty("本次参保日期")
    private Long insuredDate; // 本次参保日期

    @ApiModelProperty("首次参保年月")
    private Long firstInsuredDate; // 首次参保年月

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(String insuranceType) {
        this.insuranceType = insuranceType;
    }

    public String getInsuredStatus() {
        return insuredStatus;
    }

    public void setInsuredStatus(String insuredStatus) {
        this.insuredStatus = insuredStatus;
    }

    public Long getInsuredDate() {
        return insuredDate;
    }

    public void setInsuredDate(Long insuredDate) {
        this.insuredDate = insuredDate;
    }

    public Long getFirstInsuredDate() {
        return firstInsuredDate;
    }

    public void setFirstInsuredDate(Long firstInsuredDate) {
        this.firstInsuredDate = firstInsuredDate;
    }
}
