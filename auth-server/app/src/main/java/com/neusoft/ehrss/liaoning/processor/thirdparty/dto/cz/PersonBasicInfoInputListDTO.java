package com.neusoft.ehrss.liaoning.processor.thirdparty.dto.cz;

import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Description: 职工基本信息查询无过滤 入参DTO
 * Author: tianmx
 * CreateTime: 2020/3/7
 */
@XmlRootElement
public class PersonBasicInfoInputListDTO {
    private String socialSecurityNumber; // 社会保障号码
    private String certificateType; // 证件类型
    private String certificateNumber; // 证件号码
    private String name; // 姓名
    private List<String> profileStatusList;


    public PersonBasicInfoInputListDTO(){};

    public PersonBasicInfoInputListDTO(String socialSecurityNumber, String certificateType, String certificateNumber, String name) {
        this.socialSecurityNumber = socialSecurityNumber;
        this.certificateType = certificateType;
        this.certificateNumber = certificateNumber;
        this.name = name;
    }
    public PersonBasicInfoInputListDTO(String socialSecurityNumber, String certificateType, String certificateNumber, String name,List<String> profileStatusList) {
        this.socialSecurityNumber = socialSecurityNumber;
        this.certificateType = certificateType;
        this.certificateNumber = certificateNumber;
        this.name = name;
        this.profileStatusList = profileStatusList;
    }
    public List<String> getProfileStatusList() {
        return profileStatusList;
    }

    public void setProfileStatusList(List<String> profileStatusList) {
        this.profileStatusList = profileStatusList;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
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
}
