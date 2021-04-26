package com.neusoft.sl.si.authserver.uaa.validate.identity;


import com.neusoft.sl.girder.ddd.hibernate.vo.ValueObjectBase;

/**
 * 抽象证件类型
 * 
 * @author wuyf
 * 
 */
public abstract class AbstractCertificate extends
		ValueObjectBase<AbstractCertificate> implements Certificate {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2829065498262831537L;

	/**
	 * 证件类型
	 * 
	 * @Column(name = "AAC058")
	 */
	protected CertificateEnum certificateType;

	/**
	 * 社会保障号码
	 * 
	 * @Column(name = "AAC002")
	 */
	protected String socialEnsureNumber;

	/**
	 * 证件号码
	 * 
	 * @Column(name = "AAC147")
	 */
	protected String number;

	/**
	 * 国家/地区代码
	 * 
	 * @Column(name = "AAC161")
	 */
	protected String country = "CHN";//默认中国

	/**
	 * 获取国籍
	 * 
	 * @return
	 */
	public String getCountry() {
		return country;
	}
}
