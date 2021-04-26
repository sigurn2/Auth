package com.neusoft.ehrss.liaoning.processor.thirdparty.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * 单位信息查询DTO
 *
 * @author tianmx
 */
@XmlRootElement
public class UnitInfoOutputDTO implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -5427184855689595477L;
    /**
     * 单位基本信息ID
     */
    private Long unitId;
    /**
     * 单位编号
     */
    private String unitNumber;

    /**
     * 组织机构代码
     */
    private String organizationCode;
    /**
     * 统一社会信用代码
     */
    private String socialCreditCode;
    /**
     * 单位名称
     */
    private String name;
    /**
     * 单位类型
     */
    private String unitType;
    /**
     * 单位状态
     */
    private String profileStatus;
    /**
     * 隶属关系
     */
    private String subRelation;
    /**
     * 经济类型
     */
    private String economicsType;
    /**
     * 经营范围
     */
    private String businessScope;
    /**
     * 行业代码
     */
    private String businessCode;
    /**
     * 事业单位类型（经费来源）
     */
    private String institutionType;
    /**
     * 工商营业执照种类
     */
    private String registType;
    /**
     * 工商营业执照号码
     */
    private String registNumber;
    /**
     * 工商营业发照日期
     */
    private Long publishDate;
    /**
     * 工商营业执照有效期限
     */
    private Integer validateYear;
    /**
     * 登记机关
     */
    private String publishOrg;
    /**
     * 主管部门或主管机构
     */
    private String adminUnit;
    /**
     * 批准成立单位
     */
    private String approveDepartment;
    /**
     * 批准日期
     */
    private Long approveDate;
    /**
     * 批准文号
     */
    private String approveFileNumber;
    /**
     * 法定代表人姓名
     */
    private String legalName;
    /**
     * 法定代表人身份证件类型
     */
    private String legalIdCardType;
    /**
     * 法定代表人公民身份号码
     */
    private String legalIdCardNumber;
    /**
     * 法定代表人电话
     */
    private String legalPhone;
    /**
     * 联系人
     */
    private String contactPerson;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 联系地址
     */
    private String address;
    /**
     * 邮政编码
     */
    private String zip;
    /**
     * 联系电子邮箱
     */
    private String email;
    /**
     * 传真
     */
    private String fax;
    /**
     * 税号
     */
    private String taxNumber;
    /**
     * 行政区划代码
     */
    private String stateCode;
    /**
     * 上级单位编号
     */
    private String parentUnitNumber;
    /**
     * 行业风险等级
     */
    private String industryRiskCategory;
    /**
     * 单位专管员姓名
     */
    private String unitManagerName;
    /**
     * 单位专管员所在部门
     */
    private String unitManagerDepartment;
    /**
     * 单位专管员电话
     */
    private String unitManagerPhone;
    /**
     * 单位专管员手机号码
     */
    private String unitManagerMobile;
    /**
     * 地税微机编码
     */
    private String taxUnitNumber;
    /**
     * 备注
     */
    private String comments;
    /**
     * 创建人
     */
    private String creater;
    /**
     * 创建时间
     */
    private Long createTime;
    /**
     * 创建机构编码
     */
    private String createOrgCode;
    /**
     * 经办人
     */
    private String operator;
    /**
     * 经办时间
     */
    private Long operateTime;
    /**
     * 经办机构编码
     */
    private String operateOrgCode;
    /**
     * 统筹区
     */
    private String socialPoolCode;
    ;

    /**
     * 税务机构编码
     */
    private Long taxAgencyId;
    /**
     * 税务机构名称
     */
    private String taxAgencyName;
    /**
     * 注册地址
     */
    private String unitRegistAddress;

    /**
     * 本地化-养老保险行业
     */
    private String pensionInsTrade;
    /**
     * 本地化-单位厂办大集体核销标志
     */
    private String largeTeamFlag;
    /**
     *单位特殊类别
     * */
    private String specialType ;

    /**
     * 一级单位二级单位
     * */
    private Integer level ;

    public String getSpecialType() {
        return specialType;
    }

    public void setSpecialType(String specialType) {
        this.specialType = specialType;
    }

    public String getPensionInsTrade() {
        return pensionInsTrade;
    }

    public void setPensionInsTrade(String pensionInsTrade) {
        this.pensionInsTrade = pensionInsTrade;
    }

    public String getLargeTeamFlag() {
        return largeTeamFlag;
    }

    public void setLargeTeamFlag(String largeTeamFlag) {
        this.largeTeamFlag = largeTeamFlag;
    }

    /**
     * 默认构造函数
     */
    public UnitInfoOutputDTO() {
        // for hibernate
    }


    public Long getTaxAgencyId() {
        return taxAgencyId;
    }

    public void setTaxAgencyId(Long taxAgencyId) {
        this.taxAgencyId = taxAgencyId;
    }

    public String getTaxAgencyName() {
        return taxAgencyName;
    }

    public void setTaxAgencyName(String taxAgencyName) {
        this.taxAgencyName = taxAgencyName;
    }

    public String getUnitRegistAddress() {
        return unitRegistAddress;
    }

    public void setUnitRegistAddress(String unitRegistAddress) {
        this.unitRegistAddress = unitRegistAddress;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getSocialCreditCode() {
        return socialCreditCode;
    }

    public void setSocialCreditCode(String socialCreditCode) {
        this.socialCreditCode = socialCreditCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getProfileStatus() {
        return profileStatus;
    }

    public void setProfileStatus(String profileStatus) {
        this.profileStatus = profileStatus;
    }

    public String getSubRelation() {
        return subRelation;
    }

    public void setSubRelation(String subRelation) {
        this.subRelation = subRelation;
    }

    public String getEconomicsType() {
        return economicsType;
    }

    public void setEconomicsType(String economicsType) {
        this.economicsType = economicsType;
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getInstitutionType() {
        return institutionType;
    }

    public void setInstitutionType(String institutionType) {
        this.institutionType = institutionType;
    }

    public String getRegistType() {
        return registType;
    }

    public void setRegistType(String registType) {
        this.registType = registType;
    }

    public String getRegistNumber() {
        return registNumber;
    }

    public void setRegistNumber(String registNumber) {
        this.registNumber = registNumber;
    }

    public Long getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Long publishDate) {
        this.publishDate = publishDate;
    }

    public Integer getValidateYear() {
        return validateYear;
    }

    public void setValidateYear(Integer validateYear) {
        this.validateYear = validateYear;
    }

    public String getPublishOrg() {
        return publishOrg;
    }

    public void setPublishOrg(String publishOrg) {
        this.publishOrg = publishOrg;
    }

    public String getAdminUnit() {
        return adminUnit;
    }

    public void setAdminUnit(String adminUnit) {
        this.adminUnit = adminUnit;
    }

    public String getApproveDepartment() {
        return approveDepartment;
    }

    public void setApproveDepartment(String approveDepartment) {
        this.approveDepartment = approveDepartment;
    }

    public Long getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(Long approveDate) {
        this.approveDate = approveDate;
    }

    public String getApproveFileNumber() {
        return approveFileNumber;
    }

    public void setApproveFileNumber(String approveFileNumber) {
        this.approveFileNumber = approveFileNumber;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getLegalIdCardType() {
        return legalIdCardType;
    }

    public void setLegalIdCardType(String legalIdCardType) {
        this.legalIdCardType = legalIdCardType;
    }

    public String getLegalIdCardNumber() {
        return legalIdCardNumber;
    }

    public void setLegalIdCardNumber(String legalIdCardNumber) {
        this.legalIdCardNumber = legalIdCardNumber;
    }

    public String getLegalPhone() {
        return legalPhone;
    }

    public void setLegalPhone(String legalPhone) {
        this.legalPhone = legalPhone;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getParentUnitNumber() {
        return parentUnitNumber;
    }

    public void setParentUnitNumber(String parentUnitNumber) {
        this.parentUnitNumber = parentUnitNumber;
    }

    public String getIndustryRiskCategory() {
        return industryRiskCategory;
    }

    public void setIndustryRiskCategory(String industryRiskCategory) {
        this.industryRiskCategory = industryRiskCategory;
    }

    public String getUnitManagerName() {
        return unitManagerName;
    }

    public void setUnitManagerName(String unitManagerName) {
        this.unitManagerName = unitManagerName;
    }

    public String getUnitManagerDepartment() {
        return unitManagerDepartment;
    }

    public void setUnitManagerDepartment(String unitManagerDepartment) {
        this.unitManagerDepartment = unitManagerDepartment;
    }

    public String getUnitManagerPhone() {
        return unitManagerPhone;
    }

    public void setUnitManagerPhone(String unitManagerPhone) {
        this.unitManagerPhone = unitManagerPhone;
    }

    public String getUnitManagerMobile() {
        return unitManagerMobile;
    }

    public void setUnitManagerMobile(String unitManagerMobile) {
        this.unitManagerMobile = unitManagerMobile;
    }

    public String getTaxUnitNumber() {
        return taxUnitNumber;
    }

    public void setTaxUnitNumber(String taxUnitNumber) {
        this.taxUnitNumber = taxUnitNumber;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getCreateOrgCode() {
        return createOrgCode;
    }

    public void setCreateOrgCode(String createOrgCode) {
        this.createOrgCode = createOrgCode;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Long getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Long operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperateOrgCode() {
        return operateOrgCode;
    }

    public void setOperateOrgCode(String operateOrgCode) {
        this.operateOrgCode = operateOrgCode;
    }

    public String getSocialPoolCode() {
        return socialPoolCode;
    }

    public void setSocialPoolCode(String socialPoolCode) {
        this.socialPoolCode = socialPoolCode;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}