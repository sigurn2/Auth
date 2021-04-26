package com.neusoft.sl.si.authserver.base.domains.user;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.Validate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.neusoft.sl.girder.ddd.hibernate.entity.UuidEntityBase;
import com.neusoft.sl.si.authserver.base.domains.user.pattern.UserPattern;

@Entity
@Access(AccessType.FIELD)
@Table(name = "T_USER")
public class ThinUserPattern extends UuidEntityBase<ThinUserPattern, String> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/** 账户名 */
	@Column(unique = true)
	@NotNull
	private String account;

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

	/** 用户关联的手势解锁密码 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "userId", orphanRemoval = true)
	private Set<UserPattern> patterns = new HashSet<UserPattern>();

	public Set<UserPattern> getPatterns() {
		return Collections.unmodifiableSet(patterns);
	}

	public void addPattern(UserPattern pattern) {
		Validate.notNull(pattern, "新增关联手机解锁不能为空");
		this.patterns.add(pattern);
	}
	
	public void removePattern(UserPattern pattern) {
        Validate.notNull(pattern, "要删除的手机解锁不能为空");
        this.patterns.remove(pattern);
    }

	/**
	 * 构造函数
	 */
	protected ThinUserPattern() {
		// for jpa
	}

	public ThinUserPattern(String account, String name, String password) {
		this.account = account;
		this.name = name;
		this.password = password;
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

}
