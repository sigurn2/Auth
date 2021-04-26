package com.neusoft.sl.si.authserver.base.domains.person;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * User: tianmx
 * Date: 2019/12/25
 * Description: 个人基本信息DTO
 */
@ApiModel("人员基本信息")
@XmlRootElement
public class PersonBasicInfo {


    /**
     * 个人管理编码
     **/
    @ApiModelProperty("个人管理编码")
    private String AAC999;
    /**
     * 个人编号
     **/
    @ApiModelProperty("个人编号")
    private Long AAC001;
    /**
     * 市
     **/
    @ApiModelProperty("市")
    private String AAF117;
    /**
     * 县/区
     **/
    @ApiModelProperty("县/区")
    private String AAF116;
    /**
     * 街道
     **/
    @ApiModelProperty("街道")
    private String AAF115;
    /**
     * 社区
     **/
    @ApiModelProperty("社区")
    private String AAZ071;
    /**
     * 姓名
     **/
    @ApiModelProperty("姓名")
    private String AAC003;
    /**
     * 证件号码
     **/
    @ApiModelProperty("证件号码")
    private String AAC002;
    /**
     * 性别
     **/
    @ApiModelProperty("性别")
    private String AAC004;
    /**
     * 民族
     **/
    @ApiModelProperty("民族")
    private String AAC005;
    /**
     * 出生日期
     **/
    @ApiModelProperty("出生日期")
    private String AAC006;
    /**
     * 联系电话
     **/
    @ApiModelProperty("联系电话")
    private String AAE005;
    /**
     * 居住地址
     **/
    @ApiModelProperty("居住地址")
    private String AAE006;
    /**
     * 参保状态
     **/
    @ApiModelProperty("参保状态")
    private String AAC008;
    /**
     * 缴费状态
     **/
    @ApiModelProperty("缴费状态")
    private String AAC031;
    /**
     * 缴费档次
     **/
    @ApiModelProperty("缴费档次")
    private String AAE141;
    /**
     * 发放状态
     **/
    @ApiModelProperty("发放状态")
    private String AAE116;
    /**
     * 待遇启领时间
     **/
    @ApiModelProperty("待遇启领时间")
    private String AIC160;
    /**
     * 统筹区
     **/
    @ApiModelProperty("统筹区")
    private String AAA027;
    /**
     * 机构编码
     **/
    @ApiModelProperty("机构编码")
    private String AAB034;
    /**
     * 证件类型
     **/
    @ApiModelProperty("证件类型")
    private String AAC058;

    /**
     * 参保类型
     * @return
     */
    @ApiModelProperty("参保类型")
    private String clientType;

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getAAC999() {
        return AAC999;
    }

    public void setAAC999(String AAC999) {
        this.AAC999 = AAC999;
    }

    public Long getAAC001() {
        return AAC001;
    }

    public void setAAC001(Long AAC001) {
        this.AAC001 = AAC001;
    }

    public String getAAF117() {
        return AAF117;
    }

    public void setAAF117(String AAF117) {
        this.AAF117 = AAF117;
    }

    public String getAAF116() {
        return AAF116;
    }

    public void setAAF116(String AAF116) {
        this.AAF116 = AAF116;
    }

    public String getAAF115() {
        return AAF115;
    }

    public void setAAF115(String AAF115) {
        this.AAF115 = AAF115;
    }

    public String getAAZ071() {
        return AAZ071;
    }

    public void setAAZ071(String AAZ071) {
        this.AAZ071 = AAZ071;
    }

    public String getAAC003() {
        return AAC003;
    }

    public void setAAC003(String AAC003) {
        this.AAC003 = AAC003;
    }

    public String getAAC002() {
        return AAC002;
    }

    public void setAAC002(String AAC002) {
        this.AAC002 = AAC002;
    }

    public String getAAC004() {
        return AAC004;
    }

    public void setAAC004(String AAC004) {
        this.AAC004 = AAC004;
    }

    public String getAAC005() {
        return AAC005;
    }

    public void setAAC005(String AAC005) {
        this.AAC005 = AAC005;
    }

    public String getAAC006() {
        return AAC006;
    }

    public void setAAC006(String AAC006) {
        this.AAC006 = AAC006;
    }

    public String getAAE005() {
        return AAE005;
    }

    public void setAAE005(String AAE005) {
        this.AAE005 = AAE005;
    }

    public String getAAE006() {
        return AAE006;
    }

    public void setAAE006(String AAE006) {
        this.AAE006 = AAE006;
    }

    public String getAAC008() {
        return AAC008;
    }

    public void setAAC008(String AAC008) {
        this.AAC008 = AAC008;
    }

    public String getAAC031() {
        return AAC031;
    }

    public void setAAC031(String AAC031) {
        this.AAC031 = AAC031;
    }

    public String getAAE141() {
        return AAE141;
    }

    public void setAAE141(String AAE141) {
        this.AAE141 = AAE141;
    }

    public String getAAE116() {
        return AAE116;
    }

    public void setAAE116(String AAE116) {
        this.AAE116 = AAE116;
    }

    public String getAIC160() {
        return AIC160;
    }

    public void setAIC160(String AIC160) {
        this.AIC160 = AIC160;
    }

    public String getAAA027() {
        return AAA027;
    }

    public void setAAA027(String AAA027) {
        this.AAA027 = AAA027;
    }

    public String getAAB034() {
        return AAB034;
    }

    public void setAAB034(String AAB034) {
        this.AAB034 = AAB034;
    }

    public String getAAC058() {
        return AAC058;
    }

    public void setAAC058(String AAC058) {
        this.AAC058 = AAC058;
    }
}
