package com.neusoft.sl.girder.security.oauth2.domain;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 单位数据传输对象
 *
 * @author mojf
 *
 */
public class CompanyDTO implements Serializable {
    /**
     * serialVersionUIDS
     */
    private static final long serialVersionUID = 1890147100375221210L;

    /** 单位Id */
    //@JSONField(name="aab001")
    private Long id;

    /** 单位编号 */
    @JSONField(name="aab001")
    private String companyNumber;

    /** 单位名称 */
    @JSONField(name="aab004")
    private String name;

    /** 单位代码 **/
    @JSONField(name="aab998")
    private String orgCode;

    private String clientType;


    private String socialPoolCode;

    /**
     * 单位级别
     */
    private Integer level;

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the companyNumber
     */
    public String getCompanyNumber() {
        return companyNumber;
    }

    /**
     * @param companyNumber the companyNumber to set
     */
    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }


    public String getSocialPoolCode() {
        return socialPoolCode;
    }

    public void setSocialPoolCode(String socialPoolCode) {
        this.socialPoolCode = socialPoolCode;
    }

    @Override
    public String toString() {
        return "CompanyDTO{" +
                "id=" + id +
                ", companyNumber='" + companyNumber + '\'' +
                ", name='" + name + '\'' +
                ", orgCode='" + orgCode + '\'' +
                ", clientType='" + clientType + '\'' +
                ", socialPoolCode='" + socialPoolCode + '\'' +
                ", level=" + level +
                '}';
    }
}
