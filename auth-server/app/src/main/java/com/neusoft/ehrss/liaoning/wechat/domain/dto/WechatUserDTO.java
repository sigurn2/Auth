package com.neusoft.ehrss.liaoning.wechat.domain.dto;

import com.neusoft.sl.si.authserver.uaa.controller.interfaces.user.dto.PersonUserDTO;

import io.swagger.annotations.ApiModelProperty;

/**
 * 微信
 * 
 * @author ZHOUHD
 *
 */
public class WechatUserDTO extends PersonUserDTO {

	@ApiModelProperty(value = "微信openid")
	private String openid;

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	@Override
	public String toString() {
		return "WechatUserDTO [openid=" + openid + "]";
	}

}
