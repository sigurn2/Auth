package com.neusoft.sl.si.authserver.uaa.validate.identity;

/**
 * 表示身份证件对象
 * <p>
 * 身份证件抽象类，身份证件包括身份证、外国人护照等
 * </p>
 */
public interface Certificate{

	/** 获得社会保障号码 */
	public String getSocialEnsureNumber();

	/** 获得证件号码 */
	public String getNumber();
	
	/** 获取国家代码 */
	public String getCountry();

	/** 获取证件类型 */
	public CertificateEnum getCertificateType();
	
	
}
