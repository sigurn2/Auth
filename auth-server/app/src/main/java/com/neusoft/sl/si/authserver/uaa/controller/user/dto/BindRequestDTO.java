package com.neusoft.sl.si.authserver.uaa.controller.user.dto;

/**
 * @program: liaoning-auth
 * @description: 绑定入参dto
 * @author: lgy
 * @create: 2020-03-06 10:45
 **/

public class BindRequestDTO {

    private String areaCode;


    private String password;

    private String account;

    private String orgCode;


    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
}
