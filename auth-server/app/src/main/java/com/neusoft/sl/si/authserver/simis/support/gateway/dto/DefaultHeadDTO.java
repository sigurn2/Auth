package com.neusoft.sl.si.authserver.simis.support.gateway.dto;


/**
 * 交易请求报文头
 */
public class DefaultHeadDTO {

    /**
     * 请求方法
     */
    private String funcId;

    /**
     * 请求参数
     */
    private String data;


    /**
     * 默认构造函数
     */
    public DefaultHeadDTO() {
        super();
    }

    public DefaultHeadDTO(String funcId) {
        this.funcId = funcId;
    }

    public String getFuncId() {
        return funcId;
    }

    public void setFuncId(String funcId) {
        this.funcId = funcId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}


