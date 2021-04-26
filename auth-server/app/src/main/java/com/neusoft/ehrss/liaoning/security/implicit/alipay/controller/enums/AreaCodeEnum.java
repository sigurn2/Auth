package com.neusoft.ehrss.liaoning.security.implicit.alipay.controller.enums;

/**
 * @program: liaoning-auth
 * @description: 行政区划枚举
 * @author: lgy
 * @create: 2020-06-19 18:40
 **/

public enum AreaCodeEnum {

    ST("219900","st"),BX("210500","benxi");

    private String areaCode;
    private String area;

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    private AreaCodeEnum(String areaCode, String area) {
        this.areaCode = areaCode;
        this.area = area;
    }

    public static String getValue(String code) {
        for (AreaCodeEnum ele : values()) {
            if(ele.getAreaCode().equals(code)) {
                return ele.getArea();
            }
        }
        return null;
    }

}
