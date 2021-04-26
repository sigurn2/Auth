package com.neusoft.sl.si.authserver.uaa.controller.user.dto;

/**
 * @program: auth
 * @description:
 * @author: lgy
 * @create: 2019-10-08 10:43
 **/

public class DealResultDTO {

    private Integer appCode;

    private String errmsg;

    public Integer getAppCode() {
        return appCode;
    }

    public void setAppCode(Integer appCode) {
        this.appCode = appCode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public DealResultDTO(Integer appCode, String errmsg) {
        this.appCode = appCode;
        this.errmsg = errmsg;
    }

    public DealResultDTO() {
    }
}
