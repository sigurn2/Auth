package com.neusoft.ehrss.liaoning.utils;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @program: liaoning-auth
 * @description: http 规范实现类
 * @author: lgy
 * @create: 2020-03-06 10:29
 **/

public abstract class HttpResultDTO {

    private String appCode;

    private String msg;

    private String data;

    public HttpResultDTO(String appCode) {
        this.appCode = appCode;
    }

    public HttpResultDTO(String appCode, String msg) {
        this.appCode = appCode;
        this.msg = msg;
    }

    public String getAppCode() {
        return appCode;
    }

    @JSONField(name= "code")
    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getMsg() {
        return msg;
    }
    @JSONField(name= "msg")
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }
    @JSONField(name= "result")
    public void setData(String data) {
        this.data = data;
    }

    public HttpResultDTO() {
    }
}
