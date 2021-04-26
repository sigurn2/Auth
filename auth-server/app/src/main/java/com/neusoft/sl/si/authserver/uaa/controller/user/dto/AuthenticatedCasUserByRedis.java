package com.neusoft.sl.si.authserver.uaa.controller.user.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.constraints.Email;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.neusoft.sl.girder.security.oauth2.domain.CompanyDTO;
import com.neusoft.sl.girder.security.oauth2.domain.PersonDTO;

/**
 * User 数据传输对象
 * 
 * @author mojf
 *
 */
@JsonInclude(Include.ALWAYS)
public class AuthenticatedCasUserByRedis implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1074559970343717883L;
	/** 账户名 */
	private String account;
	/** 用户名 */
	private String name;
	/** 电子邮件 */
	@Email
	private String email;
	/** 手机号码 */
	private String mobile;
	/** 身份证号码 */
	private String idNumber;
	/** 关联单位 */
	private List<CompanyDTO> associatedCompanys;
	/** 关联个人 */
	private List<PersonDTO> associatedPersons;
	/** 用户类型 */
	private String userType;

	private String accessToken;
	private String tokenType;
	private int expiresIn;
	private Set<String> scope;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * @param account
	 *            the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile
	 *            the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the idNumber
	 */
	public String getIdNumber() {
		return idNumber;
	}

	/**
	 * @param idNumber
	 *            the idNumber to set
	 */
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	/**
	 * @return the associatedCompanys
	 */
	public List<CompanyDTO> getAssociatedCompanys() {
		return associatedCompanys;
	}

	/**
	 * @param associatedCompanys
	 *            the associatedCompanys to set
	 */
	public void setAssociatedCompanys(List<CompanyDTO> associatedCompanys) {
		this.associatedCompanys = associatedCompanys;
	}

	/**
	 * @return the associatedPersons
	 */
	public List<PersonDTO> getAssociatedPersons() {
		return associatedPersons;
	}

	/**
	 * @param associatedPersons
	 *            the associatedPersons to set
	 */
	public void setAssociatedPersons(List<PersonDTO> associatedPersons) {
		this.associatedPersons = associatedPersons;
	}

	/**
	 * @return the userType
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * @param userType
	 *            the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

	public Set<String> getScope() {
		return scope;
	}

	public void setScope(Set<String> scope) {
		this.scope = scope;
	}

	/**
	 * 传入的Person Id是否包含在用户关联的个人中
	 * 
	 * @param id
	 * @return
	 */
	public boolean canAccessPersonById(Long id) {
		if (id == null) {
			return false;
		}
		for (PersonDTO person : this.getAssociatedPersons()) {
			if (id.equals(person.getId())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 传入的Company Id是否包含在用户关联的单位中
	 * 
	 * @param id
	 * @return
	 */
	public boolean canAccessCompanyById(Long id) {
		if (id == null) {
			return false;
		}
		for (CompanyDTO company : this.getAssociatedCompanys()) {
			if (id.equals(company.getId())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 传入的Person Number是否包含在用户关联的个人中
	 * 
	 * @param id
	 * @return
	 */
	public boolean canAccessPersonByNumber(String personNumber) {
		if (personNumber == null) {
			return false;
		}
		for (PersonDTO person : this.getAssociatedPersons()) {
			if (personNumber.equals(person.getPersonNumber())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 传入的Company Number是否包含在用户关联的单位中
	 * 
	 * @param id
	 * @return
	 */
	public boolean canAccessCompanyByNumber(String companyNumber) {
		if (companyNumber == null) {
			return false;
		}
		for (CompanyDTO company : this.getAssociatedCompanys()) {
			if (companyNumber.equals(company.getCompanyNumber())) {
				return true;
			}
		}
		return false;
	}

}
