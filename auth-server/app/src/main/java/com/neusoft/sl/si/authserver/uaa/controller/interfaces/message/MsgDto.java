package com.neusoft.sl.si.authserver.uaa.controller.interfaces.message;

import java.io.Serializable;

/**
 * 发送信息内容类
 * 
 * @author youty
 * 
 */
public class MsgDto implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private String title;
	// 发送内容
	private String content;
	private String url;
	// 手机号码
	private String mobile;
	// 邮件号码
	private String email;
	// web标识
	private String webacc;
	// 姓名
	private String name;
	// 渠道编号
	// 定点医药机构网上申请(药店) DDJGWSSQYD
	// 定点医疗机构网上申请(机构) DDJGWSSQJG
	// 个人网厅账号注册 GRWSBS1
	// 个人手机号码修改 GRWSBS2
	// 个人登录密码修改 GRWSBS3
	// 个人登录密码重置 GRWSBS4
	private String channel;
	// 用户编号 保障中心普通用户 20000
	private String sender;

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebacc() {
		return webacc;
	}

	public void setWebacc(String webacc) {
		this.webacc = webacc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
