package com.neusoft.sl.si.authserver.uaa.controller.interfaces.user.dto;

import io.swagger.annotations.ApiModelProperty;

public class ZwfwUpdatePwdDTO {

    @ApiModelProperty("service")
    private String service;
    @ApiModelProperty("method")
    private String method;
    @ApiModelProperty("version")
    private String version;
    @ApiModelProperty("token")
    private String token;
    @ApiModelProperty("key")
    private String key;
    @ApiModelProperty("oldPassword")
    private String oldPassword;
    @ApiModelProperty("newPassword")
    private String newPassword;

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





    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public ZwfwUpdatePwdDTO(String oldPassword, String newPassword, String token) {
        this.key="E2A243476964ABAF584C7DFA76A6F949";
        this.service="user";
        this.method="updatePwd";
        this.version ="1.0.0";
        this.token=token;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}
