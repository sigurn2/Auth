package com.neusoft.sl.si.authserver.uaa.controller.interfaces.message;

import lombok.Data;

@Data
public class ForgetMsgDTO {

    private String appNumber;

    private String content;

    private String mobile;


    public String getAppNumber() {
        return appNumber;
    }

    public void setAppNumber(String appNumber) {
        this.appNumber = appNumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
