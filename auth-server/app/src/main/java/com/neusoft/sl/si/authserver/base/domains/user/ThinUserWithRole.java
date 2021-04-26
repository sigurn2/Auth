package com.neusoft.sl.si.authserver.base.domains.user;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.neusoft.sl.girder.ddd.hibernate.entity.UuidEntityBase;
import com.neusoft.sl.si.authserver.base.domains.role.Role;

@Entity
@Access(AccessType.FIELD)
@Table(name = "T_USER")
public class ThinUserWithRole extends UuidEntityBase<ThinUserWithRole, String> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 2544115627337488873L;

	/** 账户名 */
	@Column(unique = true)
	@NotNull
	private String account;
	// 对称性密文
	private String salt;
	/**
	 * 用户名
	 */
	@NotNull
	private String name;

	/**
	 * 用户密码
	 */
	private String password;

	/**
	 * 证件类型
	 */
	@Column(name = "ID_TYPE")
	private String idType;

	/**
	 * 证件号码
	 */
	@Column(unique = true, name = "ID_NUMBER")
	private String idNumber;
	/**
	 * 用户类型
	 */
	@Column(name = "TYPE")
	private String type;
	/**
	 * 手机号码
	 */
	@Column(unique = true)
	private String mobile;

	/** 邮箱 **/
	private String email;

	/** 是否可用 */
	@NotNull
	private boolean activated = false;

	/** 是否通过实名认证 */
	@NotNull
	@Column(name = "REAL_NAME_AUTHED")
	private boolean realNameAuthed = false;

	/** 是否绑定社保卡 **/
	@NotNull
	@Column(name = "BIND_CARD_AUTHED")
	private boolean bindCardAuthed = false;

	@Column(name = "ORG_CODE")
	private String orgCode;

	@Column(name = "EXPERT_ACCOUNT")
	private String expertAccount;

	/** 用户角色 */
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "T_USER_ROLE", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
	private Set<Role> roles = new HashSet<Role>();

	/**
	 * 构造函数
	 */
	protected ThinUserWithRole() {
		// for jpa
	}

	public ThinUserWithRole(String account, String name, String password) {
		this.account = account;
		this.name = name;
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	@Override
	@JsonIgnoreProperties
	public String getPK() {
		return account;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
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

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
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

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	/**
	 * @return the realNameAuthed
	 */
	public boolean isRealNameAuthed() {
		return realNameAuthed;
	}

	/**
	 * @param realNameAuthed
	 *            the realNameAuthed to set
	 */
	public void setRealNameAuthed(boolean realNameAuthed) {
		this.realNameAuthed = realNameAuthed;
	}

	/**
	 * 获取用户类型
	 * 
	 * @return
	 */
	public String getUserTypeString() {
		return this.type;
	}

	public boolean isBindCardAuthed() {
		return bindCardAuthed;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getExpertAccount() {
		return expertAccount;
	}

	public void setExpertAccount(String expertAccount) {
		this.expertAccount = expertAccount;
	}

	public Set<Role> getRoles() {
		return Collections.unmodifiableSet(roles);
	}

	/**
	 * 获取用户角色名称
	 * 
	 * @param role
	 */
	public Set<String> getRolesName() {
		Set<String> rolesSet = new HashSet<String>();
		for (Role role : getRoles()) {
			rolesSet.add(role.getName());
		}
		return Collections.unmodifiableSet(rolesSet);
	}

}
