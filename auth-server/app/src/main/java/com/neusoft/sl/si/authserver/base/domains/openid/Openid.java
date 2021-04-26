package com.neusoft.sl.si.authserver.base.domains.openid;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.neusoft.sl.girder.ddd.hibernate.entity.UuidEntityBase;

/**
 * 微信openid表
 * 
 * @author zhou.haidong
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "T_OPENID")
public class Openid extends UuidEntityBase<Openid, String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 微信OPENID
	 */
	@Column(name = "OPENID")
	private String openid;

	/**
	 * 用户ID
	 */
	@Column(name = "ID_NUMBER")
	private String idNumber;

	/**
	 * 微信公众号类型
	 */
	@Column(name = "WECHAT_ACCOUNT_TYPE")
	private String wechatAccountType;

	@Override
	public String getPK() {
		return getId();
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getWechatAccountType() {
		return wechatAccountType;
	}

	public void setWechatAccountType(String wechatAccountType) {
		this.wechatAccountType = wechatAccountType;
	}

}
