package com.neusoft.sl.si.authserver.uaa.validate.identity;

import java.util.HashMap;
import java.util.Map;

/**
 * 证件类型代码
 * <p>
 * 此类支持如下特性
 * <ul>
 * <li>enum的持久化</li>
 * <li>对Person类Component属性Certificate对象的继承持久化</li> 
 * </ul>
 * </p>
 * @author wuyf
 * 
 */
public enum CertificateEnum {

	居民身份证("01"),
	中国人民解放军军官证("02"),
	中国人民武装警察警官证("03"), 
	香港特区护照("04"),
	澳门特区护照("05"), 
	台湾居民来往大陆通行证("06"),
	外国人永久居留证("07"),
	外国人护照("08"),
	残疾人证("09"),
	军烈属证明("10"),
	外国人就业证("11"),
	外国专家证("12"),
	外国人常驻记者证("13"),
	台港澳人员就业证("14"),
	回大陆定居专家证("15"),
	户口簿("21"),
	其他身份证("99");

	/** 代码值 */
	private String value;

	/** String Map */
	private static Map<String, CertificateEnum> valueMap = new HashMap<String, CertificateEnum>();

	/**初始化代码表*/
	static {
		for (CertificateEnum _enum : CertificateEnum.values()) {
			valueMap.put(_enum.value, _enum);
		}
	}
	
	/**
	 * 构造函数
	 * 
	 * @param value
	 */
	CertificateEnum(String value) {
		this.value = value;
	};

	/**
	 * 获取enum对象
     *@see GenericEnumUserType
     */
	public static CertificateEnum getEnum(String value) {
		return valueMap.get(value);
	}

	/**
	 * 存储enum值
     *@see GenericEnumUserType
     */
	public String toString() {
		return value;
	}
}
