package com.neusoft.ehrss.liaoning.processor.thirdparty.dto.cz;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Description: 在职基本信息DTO
 * Author: li.jinpeng
 * CreateTime: 2020/3/7
 */
@Data
@ApiModel("在职基本信息查询")
@XmlRootElement
public class PersonEmpInfoDTO {

    @ApiModelProperty("个人身份")
    private String identityMark; // 个人身份

    @ApiModelProperty("养老账户类别")
    private String pensionActType; // 养老账户类别

    @ApiModelProperty("用工形式")
    private String formOfEmployment; // 用工形式

    @ApiModelProperty("特殊工种类别")
    private String specialWorkType; // 特殊工种类别

    @ApiModelProperty("参加工作日期")
    private Long workDate; // 参加工作日期

    @ApiModelProperty("干部标志")
    private String cadreMark; // 干部标志

    @ApiModelProperty("退役军人类别")
    private String veteransType; // 退役军人类别

    @ApiModelProperty("雇主标志")
    private String employerMark; // 雇主标志

    @ApiModelProperty("农民工标志")
    private String migrantWorkers; // 农民工标志

    @ApiModelProperty("养老建账年月")
    private Long pensionAccountDate; // 养老建账年月

    @ApiModelProperty("养老视同缴费月数")
    private Integer pensionDeemedMonths; // 养老视同缴费月数

    @ApiModelProperty("建账户前个人缴费月数")
    private Integer pensionAccountPreMonths; // 建账户前个人缴费月数

    @ApiModelProperty("外国人免缴标志")
    private String foreignersExemptionMark; // 外国人免缴标志

    @ApiModelProperty("五七家属工标识")
    private String familyWorkersMark; // 五七家属工标识

    @ApiModelProperty("是否本地退休")
    private String localRetirement; // 是否本地退休

    public String getIdentityMark() {
        return identityMark;
    }

    public void setIdentityMark(String identityMark) {
        this.identityMark = identityMark;
    }

    public String getPensionActType() {
        return pensionActType;
    }

    public void setPensionActType(String pensionActType) {
        this.pensionActType = pensionActType;
    }

    public String getFormOfEmployment() {
        return formOfEmployment;
    }

    public void setFormOfEmployment(String formOfEmployment) {
        this.formOfEmployment = formOfEmployment;
    }

    public String getSpecialWorkType() {
        return specialWorkType;
    }

    public void setSpecialWorkType(String specialWorkType) {
        this.specialWorkType = specialWorkType;
    }

    public Long getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Long workDate) {
        this.workDate = workDate;
    }

    public String getCadreMark() {
        return cadreMark;
    }

    public void setCadreMark(String cadreMark) {
        this.cadreMark = cadreMark;
    }

    public String getVeteransType() {
        return veteransType;
    }

    public void setVeteransType(String veteransType) {
        this.veteransType = veteransType;
    }

    public String getEmployerMark() {
        return employerMark;
    }

    public void setEmployerMark(String employerMark) {
        this.employerMark = employerMark;
    }

    public String getMigrantWorkers() {
        return migrantWorkers;
    }

    public void setMigrantWorkers(String migrantWorkers) {
        this.migrantWorkers = migrantWorkers;
    }

    public Long getPensionAccountDate() {
        return pensionAccountDate;
    }

    public void setPensionAccountDate(Long pensionAccountDate) {
        this.pensionAccountDate = pensionAccountDate;
    }

    public Integer getPensionDeemedMonths() {
        return pensionDeemedMonths;
    }

    public void setPensionDeemedMonths(Integer pensionDeemedMonths) {
        this.pensionDeemedMonths = pensionDeemedMonths;
    }

    public Integer getPensionAccountPreMonths() {
        return pensionAccountPreMonths;
    }

    public void setPensionAccountPreMonths(Integer pensionAccountPreMonths) {
        this.pensionAccountPreMonths = pensionAccountPreMonths;
    }

    public String getForeignersExemptionMark() {
        return foreignersExemptionMark;
    }

    public void setForeignersExemptionMark(String foreignersExemptionMark) {
        this.foreignersExemptionMark = foreignersExemptionMark;
    }

    public String getFamilyWorkersMark() {
        return familyWorkersMark;
    }

    public void setFamilyWorkersMark(String familyWorkersMark) {
        this.familyWorkersMark = familyWorkersMark;
    }

    public String getLocalRetirement() {
        return localRetirement;
    }

    public void setLocalRetirement(String localRetirement) {
        this.localRetirement = localRetirement;
    }
}
