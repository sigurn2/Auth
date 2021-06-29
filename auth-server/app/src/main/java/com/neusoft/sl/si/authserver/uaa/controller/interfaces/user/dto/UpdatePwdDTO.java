package com.neusoft.sl.si.authserver.uaa.controller.interfaces.user.dto;

import io.swagger.annotations.ApiModelProperty;

public class UpdatePwdDTO {
    /** 新密码 */
    @ApiModelProperty(value = "账号名")
    private String account;
    /** 新密码 */
    @ApiModelProperty(value = "新密码")
    private String newPassword;
    /** 旧密码 **/
    @ApiModelProperty(value = "身份证号码")
    private String oldPassword;


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}
