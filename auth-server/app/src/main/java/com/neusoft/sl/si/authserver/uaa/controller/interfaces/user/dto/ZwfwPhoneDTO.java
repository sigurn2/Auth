package com.neusoft.sl.si.authserver.uaa.controller.interfaces.user.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @program: liaoning-auth
 * @description:
 * @author: lgy
 * @create: 2020-03-10 18:00
 **/

public class ZwfwPhoneDTO {


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

    private String phone;
    private String key;
    private String score;

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }



    public ZwfwPhoneDTO(String phone) {
        this.key="E2A243476964ABAF584C7DFA76A6F949";
        this.service="user";
        this.score="1";
        this.method="finduserbyphone";
        this.version ="1.0.0";
        this.token="00000000000000000000000000000000";
        this.phone = phone;
    }


}

