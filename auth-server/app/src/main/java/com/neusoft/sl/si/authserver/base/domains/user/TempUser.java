package com.neusoft.sl.si.authserver.base.domains.user;

import com.neusoft.sl.girder.ddd.hibernate.entity.UuidEntityBase;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @program: liaoning-auth
 * @description: 临时用户表
 *
 *  负责地市接入省网厅的用户临时存储
 *  存入不同密码加密格式的用户
 *  登录之后会删除本表数据
 *
 *
 * @author: lgy
 * @create: 2020-06-19 15:28
 **/
@Entity
@Access(AccessType.FIELD)
@Table(name = "TEMP_USER")
public class TempUser extends UuidEntityBase<TempUser, String> {


    /** 账户名 */
    @Column(name = "USERNAME")
    @NotNull
    private String username;

    /** 账户名 */
    @NotNull
    @Column(name = "PASSWORD")
    private String password;

    /** 账户名 */
    @Column(name = "AREACODE")
    @NotNull
    private String areaCode;

    /** 账户名 */
    /**
     * 0 有效
     * 1 无效
     */
    @Column(name = "LOCK_FLAG")
    @NotNull
    private String lockFlag;

    /** 账户名 */
    @Column(name = "COMPANY_NUMBER")
    @NotNull
    private String companyNumber;


    /** 单位名称 */
    @Column(name = "COMPANY_NAME")
    @NotNull
    private String companyName;

    /** 统一信用代码 */
    @Column(name = "ORG_CODE")
    private String orgCode;

    public TempUser() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getLockFlag() {
        return lockFlag;
    }

    public void setLockFlag(String lockFlag) {
        this.lockFlag = lockFlag;
    }

    public String getCompanyNumber() {
        return companyNumber;
    }

    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }


    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 2544115627337488873L;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String getPK() {
        return null;
    }

    @Override
    public String toString() {
        return "TempUser{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", areaCode='" + areaCode + '\'' +
                ", lockFlag='" + lockFlag + '\'' +
                ", companyNumber='" + companyNumber + '\'' +
                ", companyName='" + companyName + '\'' +
                ", orgCode='" + orgCode + '\'' +
                '}';
    }
}
