package com.neusoft.sl.si.authserver.uaa.controller.interfaces.user.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @program: liaoning-auth
 * @description:
 * @author: lgy
 * @create: 2020-03-10 18:00
 **/

public class ZwfwResetPasswordDTO {

    @ApiModelProperty("账户名")
    private String userName;
    @ApiModelProperty("service")
    private String service;
    @ApiModelProperty("method")
    private String method;
    @ApiModelProperty("version")
    private String version;
    @ApiModelProperty("token")
    private String token;
    @ApiModelProperty("手机号码")
    private String mobile;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("生日")
    private String birthday;
    @ApiModelProperty("人员类型")
    private String score;
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String username) {
        this.userName = username;
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


    public ZwfwResetPasswordDTO(String username,  String mobile, String password) {
        this.key="E2A243476964ABAF584C7DFA76A6F949";
        this.service="user";
        this.score="1";
        this.method="resetPwd";
        this.version ="1.0.0";
        this.token="00000000000000000000000000000000";
        this.userName = username;
        this.mobile = mobile;
        this.password = password;
    }


}
