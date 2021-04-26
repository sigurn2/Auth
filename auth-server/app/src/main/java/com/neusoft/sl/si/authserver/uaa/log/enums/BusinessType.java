package com.neusoft.sl.si.authserver.uaa.log.enums;

/**
 * 消息业务
 * 
 * @author y_zhang.neu
 *
 */
public enum BusinessType {
	Default, // 默认，无匹配使用
	Register, // 注册
	PwdReset, // 密码重置
	PwdModify, // 密码修改
	Mobile, // 手机操作
	Login, // 登录
	Simis;// 中心端
}
