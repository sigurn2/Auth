package com.neusoft.sl.si.authserver.uaa.controller.user.dto;

import com.neusoft.sl.girder.ddd.hibernate.entity.UuidEntityBase;
import com.neusoft.sl.si.authserver.base.domains.person.PersonNumber;
import com.neusoft.sl.si.authserver.base.domains.user.ThinUser;
import org.springframework.context.annotation.Primary;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @program: liaoning-auth
 * @description: 激活码用户名绑定实体
 * @author: lgy
 * @create: 2020-02-21 14:55
 **/

@Entity
@Access(AccessType.FIELD)
@Table(name = "T_USER_ACTIVE")
public class UserActive extends UuidEntityBase<UserActive, String> {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 2544115612143488873L;

    @Column(name = "ID")
    private String id;

    @Column(name = "AREA_ACCOUNT")
    @NotNull
    private String areaAccount;


    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "AREA_CODE")
    @NotNull
    private String areaCode;

    @Column(name = "ORGCODE")
    @NotNull
    private String orgCode;


    public String getAreaAccount() {
        return areaAccount;
    }

    public void setAreaAccount(String areaAccount) {
        this.areaAccount = areaAccount;
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

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    @Override
    public String getPK() {
        return id;
    }
}
