package com.neusoft.sl.si.authserver.simis.support.gateway.dto;

/**
 * @program: liaoning-auth
 * @description: 就业个人信息入参
 * @author: lgy
 * @create: 2020-03-27 13:04
 **/

public class AC01DTO {

    /**
     * 身份证
     */
    private String aac002;


    public AC01DTO() {
    }

    public AC01DTO(String aac002) {
        this.aac002 = aac002;
    }

    public String getAac002() {
        return aac002;
    }

    public void setAac002(String aac002) {
        this.aac002 = aac002;
    }
}
