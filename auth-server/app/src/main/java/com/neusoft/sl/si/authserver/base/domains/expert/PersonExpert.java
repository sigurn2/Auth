package com.neusoft.sl.si.authserver.base.domains.expert;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Access(AccessType.FIELD)
@Table(name = "PERSON_EXPERT")
public class PersonExpert implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 专家编号
	 */
	@Id
	@Column(name = "ID")
	private String id;

	/**
	 * 专家编号
	 */
	@Column(name = "PERSONNUMBER")
	private String personNumber;

	@Column(name = "name")
	private String name;

	@Column(name = "SEX")
	private String sex;

	@Column(name = "MOBILE")
	private String mobile;

	@Column(name = "ID_NUMBER")
	private String idNumber;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PersonExpert() {
		super();
	}

	public String getPersonNumber() {
		return personNumber;
	}

	public void setPersonNumber(String personNumber) {
		this.personNumber = personNumber;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

}
