package com.neusoft.sl.si.authserver.uaa.controller.interfaces.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ForgetPwdDTO {
    /** 身份证号 */
    @ApiModelProperty(value = "身份证号")
    private String idNumber;
    /** 新密码 */
    @ApiModelProperty(value = "新密码")
    private String newPassword;





}
