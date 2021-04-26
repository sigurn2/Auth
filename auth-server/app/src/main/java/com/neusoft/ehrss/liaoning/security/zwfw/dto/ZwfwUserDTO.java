package com.neusoft.ehrss.liaoning.security.zwfw.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @program: liaoning-auth
 * @description:
 * @author: lgy
 * @create: 2020-02-17 15:33
 **/

public class ZwfwUserDTO {

    private String username;

    private String idNumber;


    private String name;

    private String mobile;

    private String uuid;


    //法人
    private String relperPerson;


    private String scope;

    //统一信用代码
    private String profession;


    //经办人身份证号
    private String jbrIdNumber;

    //经办人手机号
    private String jbrMobile;


    private String companyName;

    private String token;


    //自然人实名状态
    private String realnameAuth;


    //法人实名状态
    private String corpRealnameAuth;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCorpRealnameAuth() {
        return corpRealnameAuth;
    }
    @JSONField(name = "IDSEXT_corpAuth")
    public void setCorpRealnameAuth(String corpRealnameAuth) {
        this.corpRealnameAuth = corpRealnameAuth;
    }

    public String getRealnameAuth() {
        return realnameAuth;
    }

    @JSONField(name = "IDSEXT_relNameAuth")
    public void setRealnameAuth(String realnameAuth) {
        this.realnameAuth = realnameAuth;
    }

    public String getToken() {
        return token;
    }

    @JSONField(name= "TOKEN")
    public void setToken(String token) {
        this.token = token;
    }

    public String getIdNumber() {
        return idNumber;
    }

    @JSONField(name= "creditID")
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getName() {
        return name;
    }
    @JSONField(name = "trueName")
    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }
    @JSONField(name = "mobile")
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public String getRelperPerson() {
        return relperPerson;
    }
    @JSONField(name = "RELPERSON")
    public void setRelperPerson(String relperPerson) {
        this.relperPerson = relperPerson;
    }

    public String getScope() {
        return scope;
    }
    @JSONField(name = "SCORE")
    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getProfession() {
        return profession;
    }


    public String getUsername() {
        return username;
    }
    @JSONField(name = "USERNAME")
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "ZwfwUserDTO{" +
                "username='" + username + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", relperPerson='" + relperPerson + '\'' +
                ", scope='" + scope + '\'' +
                ", profession='" + profession + '\'' +
                ", jbrIdNumber='" + jbrIdNumber + '\'' +
                ", jbrMobile='" + jbrMobile + '\'' +
                ", companyName='" + companyName + '\'' +
                '}';
    }

    @JSONField(name = "PROFESSION")
    public void setProfession(String profession) {
        this.profession = profession;
    }



    public String getJbrIdNumber() {
        return jbrIdNumber;
    }


    @JSONField(name = "IDSEXT_jbrCreditID")
    public void setJbrIdNumber(String jbrIdNumber) {
        this.jbrIdNumber = jbrIdNumber;
    }


    public String getJbrMobile() {
        return jbrMobile;
    }

    @JSONField(name = "IDSEXT_jbrtel")
    public void setJbrMobile(String jbrMobile) {
        this.jbrMobile = jbrMobile;
    }

    public String getCompanyName() {
        return companyName;
    }
    @JSONField(name = "CORPNAME")
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
