package com.neusoft.sl.si.authserver.uaa.controller.interfaces.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ForgetPwdDTO {
    /** 账户名 */
    @ApiModelProperty(value = "账号名")
    private String account;
    /** 新密码 */
    @ApiModelProperty(value = "新密码")
    private String newPassword;

    @ApiModelProperty(value = "手机号")
    private String mobile;




}
