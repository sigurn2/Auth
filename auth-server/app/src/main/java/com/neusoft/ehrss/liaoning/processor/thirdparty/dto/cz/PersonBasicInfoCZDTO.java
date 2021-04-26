package com.neusoft.ehrss.liaoning.processor.thirdparty.dto.cz;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Description: 个人基本信息DTO
 * Author: li.jinpeng
 * CreateTime: 2020/3/7
 */
@Data
@ApiModel("职工基本信息查询")
@XmlRootElement
public class PersonBasicInfoCZDTO {
    @ApiModelProperty("个人编号")
    private Long personId; // 个人编号

    @ApiModelProperty("证件类型")
    private String certificateType; // 证件类型

    @ApiModelProperty("证件号码")
    private String certificateNumber; // 证件号码

    @ApiModelProperty("姓名")
    private String name; // 姓名

    @ApiModelProperty("性别")
    private String sex; // 性别

    @ApiModelProperty("出生日期")
    private Long birthday; // 出生日期

    @ApiModelProperty("国家/地区代码")
    private String regionCode; // 国家/地区代码

    @ApiModelProperty("户口性质")
    private String household; // 户口性质

    @ApiModelProperty("户口所在地行政区划代码")
    private String householdState; // 户口所在地行政区划代码

    @ApiModelProperty("户口所在地详细地址")
    private String householdAddress; // 户口所在地详细地址

    @ApiModelProperty("常住地行政区划代码")
    private String residentState; // 常住地行政区划代码

    @ApiModelProperty("常住地详细地址")
    private String address; // 常住地详细地址

    @ApiModelProperty("离退休状态")
    private String retireStatus; // 离退休状态

    @ApiModelProperty("参加工作日期")
    private Long workDate; // 参加工作日期

    @ApiModelProperty("离退休日期")
    private Long retireDate; // 离退休日期

    @ApiModelProperty("档案出生日期")
    private Long fileBirthDate; // 档案出生日期

    @ApiModelProperty("参保信息")
    private List<PersonInsuredInfoDTO> insuredInfoList; // 参保信息list

    @ApiModelProperty("在职信息")
    private PersonEmpInfoDTO personEmpInfo; // 在职信息

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Long getBirthday() {
        return birthday;
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getHousehold() {
        return household;
    }

    public void setHousehold(String household) {
        this.household = household;
    }

    public String getHouseholdState() {
        return householdState;
    }

    public void setHouseholdState(String householdState) {
        this.householdState = householdState;
    }

    public String getHouseholdAddress() {
        return householdAddress;
    }

    public void setHouseholdAddress(String householdAddress) {
        this.householdAddress = householdAddress;
    }

    public String getResidentState() {
        return residentState;
    }

    public void setResidentState(String residentState) {
        this.residentState = residentState;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRetireStatus() {
        return retireStatus;
    }

    public void setRetireStatus(String retireStatus) {
        this.retireStatus = retireStatus;
    }

    public Long getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Long workDate) {
        this.workDate = workDate;
    }

    public Long getRetireDate() {
        return retireDate;
    }

    public void setRetireDate(Long retireDate) {
        this.retireDate = retireDate;
    }

    public Long getFileBirthDate() {
        return fileBirthDate;
    }

    public void setFileBirthDate(Long fileBirthDate) {
        this.fileBirthDate = fileBirthDate;
    }

    @XmlElementWrapper(name = "item")
    @XmlElement(name = "items")
    public List<PersonInsuredInfoDTO> getInsuredInfoList() {
        return insuredInfoList;
    }

    public void setInsuredInfoList(List<PersonInsuredInfoDTO> insuredInfoList) {
        this.insuredInfoList = insuredInfoList;
    }

    public PersonEmpInfoDTO getPersonEmpInfo() {
        return personEmpInfo;
    }

    public void setPersonEmpInfo(PersonEmpInfoDTO personEmpInfo) {
        this.personEmpInfo = personEmpInfo;
    }
}
