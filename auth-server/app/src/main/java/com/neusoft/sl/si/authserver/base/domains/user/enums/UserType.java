package com.neusoft.sl.si.authserver.base.domains.user.enums;

/**
 * 用户类型
 * 
 * @author mojf
 * 
 */
public enum UserType {

	ADMIN, // 系统管理员
	COMPANY, // 企业用户
	PERSON, // 个人用户
	SYSTEM, // 计算机系统
	EXPERT, // 客户服务中心人员
	BUZZCONTACTOR, // 单位专管员
	SI_AGENT, // 基层受理用户
	SIMIS, // 中心端
	DEFAULT;

	public static final String USER_ADMIN = "0";
	public static final String USER_COMPANY = "1";
	public static final String USER_PERSON = "2";
	public static final String USER_SYSTEM = "3";
	public static final String USER_EXPERT = "4";
	public static final String USER_BUZZCONTACTOR = "5";
	public static final String USER_SI_AGENT = "6";

}
