package com.neusoft.sl.si.authserver.uaa.controller.interfaces.user.dto;

import io.swagger.annotations.ApiModelProperty;

public class RealAuthDTO {

    @ApiModelProperty("service")
    private String service;
    @ApiModelProperty("method")
    private String method;
    @ApiModelProperty("version")
    private String version;
    @ApiModelProperty("token")
    private String token;
    @ApiModelProperty("code")
    private String code;
    @ApiModelProperty("name")
    private String name;
    @ApiModelProperty("key")
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealAuthDTO(String code, String name) {
        this.key="E2A243476964ABAF584C7DFA76A6F949";
        this.service="check";
        this.method="pro";
        this.version ="1.0.0";
        this.token="00000000000000000000000000000000";
        this.code = code;
        this.name = name;
    }
}
