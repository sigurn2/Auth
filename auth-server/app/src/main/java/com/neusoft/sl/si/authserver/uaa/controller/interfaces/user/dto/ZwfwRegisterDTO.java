package com.neusoft.sl.si.authserver.uaa.controller.interfaces.user.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @program: liaoning-auth
 * @description:
 * @author: lgy
 * @create: 2020-03-10 16:53
 **/

public class ZwfwRegisterDTO {

    @ApiModelProperty("账户名")
    private String username;
    @ApiModelProperty("service")
    private String service;
    @ApiModelProperty("method")
    private String method;
    @ApiModelProperty("version")
    private String version;
    @ApiModelProperty("token")
    private String token;

    @ApiModelProperty("证件类型，01-身份证")
    private String credittype;
    @ApiModelProperty("证件编号")
    private String creditId;
    @ApiModelProperty("姓名")
    private String truename;
    @ApiModelProperty("手机号码")
    private String mobile;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("生日")
    private String birthday;
    @ApiModelProperty("人员类型")
    private String score;
    @ApiModelProperty("邮件地址")
    private String email;

    @ApiModelProperty("身份证起始日期")
    private String IDSEXT_certEffDate;
    @ApiModelProperty("身份证终止日期")
    private String IDSEXT_certExpDate;

     private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCredittype() {
        return credittype;
    }

    public void setCredittype(String credittype) {
        this.credittype = credittype;
    }

    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIDSEXT_certEffDate() {
        return IDSEXT_certEffDate;
    }

    public void setIDSEXT_certEffDate(String IDSEXT_certEffDate) {
        this.IDSEXT_certEffDate = IDSEXT_certEffDate;
    }

    public String getIDSEXT_certExpDate() {
        return IDSEXT_certExpDate;
    }

    public void setIDSEXT_certExpDate(String IDSEXT_certExpDate) {
        this.IDSEXT_certExpDate = IDSEXT_certExpDate;
    }

    public ZwfwRegisterDTO(String username, String creditId, String truename, String mobile, String password) {
       this.key="E2A243476964ABAF584C7DFA76A6F949";
        this.service="user";
        this.score="1";
        this.method="register";
        this.version ="1.0.0";
        this.token="00000000000000000000000000000000";
        this.setCredittype("10");
        this.username = username;
        this.credittype = credittype;
        this.creditId = creditId;
        this.truename = truename;
        this.mobile = mobile;
        this.password = password;

    }





}
