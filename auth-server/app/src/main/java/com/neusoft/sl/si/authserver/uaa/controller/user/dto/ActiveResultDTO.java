package com.neusoft.sl.si.authserver.uaa.controller.user.dto;

/**
 * @program: liaoning-auth
 * @description: 激活码激活接口
 * @author: lgy
 * @create: 2020-02-21 14:45
 **/

public class ActiveResultDTO {

    private String code;

    private String message;

    private String companyNumber;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCompanyNumber() {
        return companyNumber;
    }

    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }
}
