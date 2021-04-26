package com.neusoft.sl.si.authserver.base.domains.person.authed;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Access(AccessType.FIELD)
@Table(name = "PERSON_AUTHED_LOG")
public class PersonAuthedLogBack implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7008720510239191623L;
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "ID")
	private String id;

	@Column(name = "AUTH_DATE")
	private String authDate;
	/**
	 * atm mobile pc
	 */
	@Column(name = "AUTH_CLIENT")
	private String authClient;
	@Column(name = "PERSON_NAME")
	private String personName;
	@Column(name = "PERSON_ID_NUMBER")
	private String personIdNumber;
	@Column(name = "PERSON_MOBILE")
	private String personMobile;
	@Column(name = "PERSON_ACCOUNT")
	private String personAccount;
	@Column(name = "PERSON_ID")
	private String personId;
	/**
	 * userAuth familyAuth userCard familyCard
	 */
	@Column(name = "AUTH_TYPE")
	private String authType;
	@Column(name = "PERSON_CARD_NUMBER")
	private String personCardNumber;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAuthDate() {
		return authDate;
	}

	public void setAuthDate(String authDate) {
		this.authDate = authDate;
	}

	public String getAuthClient() {
		return authClient;
	}

	public void setAuthClient(String authClient) {
		this.authClient = authClient;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getPersonIdNumber() {
		return personIdNumber;
	}

	public void setPersonIdNumber(String personIdNumber) {
		this.personIdNumber = personIdNumber;
	}

	public String getPersonMobile() {
		return personMobile;
	}

	public void setPersonMobile(String personMobile) {
		this.personMobile = personMobile;
	}

	public String getPersonAccount() {
		return personAccount;
	}

	public void setPersonAccount(String personAccount) {
		this.personAccount = personAccount;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public PersonAuthedLogBack() {
		super();
	}

	public String getAuthType() {
		return authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	public String getPersonCardNumber() {
		return personCardNumber;
	}

	public void setPersonCardNumber(String personCardNumber) {
		this.personCardNumber = personCardNumber;
	}

}
