package com.neusoft.sl.si.authserver.base.domains.family;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.neusoft.sl.girder.ddd.hibernate.entity.UuidEntityBase;

/**
 * 家庭成员表
 * 
 * @author zhou.haidong
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "FAMILY")
public class Family extends UuidEntityBase<Family, String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "FAMILY_ID")
	private String familyId;//家庭成员id

	@Column
	private String relation;// 与当前用户的关系

	@Column
	private String name;// 真实姓名

	@Column
	private String sex;// 性别

	@Column
	private String birthday;// 出生日期

	@Column(name = "ID_NUMBER")
	private String idNumber;// 身份证号

	@Column
	private String mobile;// 联系电话（手机）

	@Column(name = "PERSON_NUMBER")
	private String personNumber;// 个人编号

	@Column(name = "ID_SOCIALENSURENUMBER")
	private String socialEnsureNumber;// 社会保障号

	@Column(name = "CARD_NUMBER")
	private String cardNumber;// 社会保障卡号

	@Column(name = "CITY_ID")
	private Integer cityId;// 地区ID

	@Column(name = "SITYPE_CODE")
	private String siTypeCode;// 医保类型编码

	@Column(name = "IS_CHRONIC")
	private boolean chronic;// 是否慢性病

	@Column(name = "IS_DEFAULT")
	private boolean def;// 默认就诊人
	
	@Column(name = "IS_AUTH")
	private boolean auth;// 是否实名认证

	@Override
	public String getPK() {
		return getId();
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPersonNumber() {
		return personNumber;
	}

	public void setPersonNumber(String personNumber) {
		this.personNumber = personNumber;
	}

	public String getSocialEnsureNumber() {
		return socialEnsureNumber;
	}

	public void setSocialEnsureNumber(String socialEnsureNumber) {
		this.socialEnsureNumber = socialEnsureNumber;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getSiTypeCode() {
		return siTypeCode;
	}

	public void setSiTypeCode(String siTypeCode) {
		this.siTypeCode = siTypeCode;
	}

	public boolean isChronic() {
		return chronic;
	}

	public void setChronic(boolean chronic) {
		this.chronic = chronic;
	}

	public boolean isDef() {
		return def;
	}

	public void setDef(boolean def) {
		this.def = def;
	}

	public String getFamilyId() {
		return familyId;
	}

	public void setFamilyId(String familyId) {
		this.familyId = familyId;
	}

	public boolean isAuth() {
		return auth;
	}

	public void setAuth(boolean auth) {
		this.auth = auth;
	}

}
