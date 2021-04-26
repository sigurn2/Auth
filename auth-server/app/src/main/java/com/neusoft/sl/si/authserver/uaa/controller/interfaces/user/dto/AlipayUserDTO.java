package com.neusoft.sl.si.authserver.uaa.controller.interfaces.user.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author gaochuan
 * @create 2018-10-30 11:09
 **/
public class AlipayUserDTO {
    @ApiModelProperty("账户名")
    private String account;
    @ApiModelProperty("证件类型，01-身份证")
    private String idType;
    @ApiModelProperty("证件编号")
    private String idNumber;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("手机号码")
    private String mobilenumber;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("密保问题")
    private String secretQuestion;
    @ApiModelProperty("个人编号")
    private String personNumber;
    @ApiModelProperty("邮件地址")
    private String email;
    @ApiModelProperty("支付宝编号")
    private String openid;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public AlipayUserDTO() {
    }

    public AlipayUserDTO(String account, String idType, String idNumber, String name, String mobilenumber, String email, String openid) {
        this.account = account;
        this.idType = idType;
        this.idNumber = idNumber;
        this.name = name;
        this.mobilenumber = mobilenumber;
        this.email = email;
        this.openid = openid;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getIdType() {
        return this.idType;
    }

    public String getSecretQuestion() {
        return this.secretQuestion;
    }

    public void setSecretQuestion(String secretQuestion) {
        this.secretQuestion = secretQuestion;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNumber() {
        return this.idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobilenumber() {
        return this.mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPersonNumber() {
        return this.personNumber;
    }

    public void setPersonNumber(String personNumber) {
        this.personNumber = personNumber;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "AlipayUserDTO{" +
                "account='" + account + '\'' +
                ", idType='" + idType + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", name='" + name + '\'' +
                ", mobilenumber='" + mobilenumber + '\'' +
                ", password='" + password + '\'' +
                ", secretQuestion='" + secretQuestion + '\'' +
                ", personNumber='" + personNumber + '\'' +
                ", email='" + email + '\'' +
                ", openid='" + openid + '\'' +
                '}';
    }
}

