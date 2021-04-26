package com.neusoft.ehrss.liaoning.utils;

/**
 * @program: liaoning-auth
 * @description:
 * @author: lgy
 * @create: 2020-03-10 12:26
 **/

public class ZwfwResultDTO<T>{

    private T data;

    private String code;

    private String message;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

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
}
