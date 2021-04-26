package com.neusoft.ehrss.liaoning.family.dto;

import java.io.Serializable;

/**
 * 家庭成员
 * 
 * @author zhou.haidong
 */
public class FamilyDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String familyId;// 家庭成员ID

	private String relation;// 与当前用户的关系

	private String name;// 真实姓名

	private String sex;// 性别

	private String birthday;// 出生日期

	private String idNumber;// 身份证号

	private String mobile;// 联系电话（手机）

	private String personNumber;// 个人编号

	private String socialEnsureNumber;// 社会保障号

	private String cardNumber;// 社会保障卡号

	private Integer cityId;// 地区ID

	private String siTypeCode;// 医保类型编码

	private boolean chronic;// 是否慢性病

	private boolean def;// 默认就诊人

	private boolean auth;// 是否实名认证

	public String getFamilyId() {
		return familyId;
	}

	public void setFamilyId(String familyId) {
		this.familyId = familyId;
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

	public boolean isAuth() {
		return auth;
	}

	public void setAuth(boolean auth) {
		this.auth = auth;
	}

}
