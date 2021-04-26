package com.neusoft.sl.si.authserver.base.domains.user;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.Validate;
import org.springframework.core.annotation.AnnotationUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.neusoft.sl.girder.ddd.hibernate.entity.UuidEntityBase;
import com.neusoft.sl.si.authserver.base.domains.company.Company;
import com.neusoft.sl.si.authserver.base.domains.family.Family;
import com.neusoft.sl.si.authserver.base.domains.org.Organization;
import com.neusoft.sl.si.authserver.base.domains.role.Role;

/**
 * 用户对象
 * 
 * @author wuyf
 * 
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "T_USER")
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING, length = 3)
@SecondaryTable(name = "T_USER_EXTEND", pkJoinColumns = { @PrimaryKeyJoinColumn(name = "USER_ID") })
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class User extends UuidEntityBase<User, String> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8443449851724394416L;

	/** 账户不能为空提示 */
	private static final String ACCOUNT_NULL_ERR = "账户不能为空";
	/** 用户名不能为空提示 */
	private static final String NAME_NULL_ERR = "用户名不能为空";
	/** 用户密码不能为空提示 */
	private static final String PASSWORD_NULL_ERR = "用户密码不能为空";

	/** 账户名 */
	@Column(unique = true)
	@NotNull
	private String account;

	/** 注册时间 **/
	@Column(name = "REGISTED_DATE")
	private String registedDate;

	/** 实名认证时间 **/
	@Column(name = "REAL_NAME_DATE")
	private String realNameDate;

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
	 * 密码盐
	 */
	private String salt;

	/**
	 * 证件类型
	 */
	@Column(name = "ID_TYPE")
	private String idType;
	/**
	 * 密保问题 以|分隔
	 */
	@Column(name = "SECRET_QUESTION")
	private String secretQuestion;
	/**
	 * 动态扩展属性
	 */
	@Column(name = "EXTENSION")
	private String extension;
	/**
	 * 身份是否验证 手机号码 邮箱 00，01，10，11格式
	 */
	@Column(name = "IDENTITY_VERIFICATION")
	private String identityVerification;
	/**
	 * 证件号码
	 */
	// @Column(unique = true, name = "ID_NUMBER")
	@Column(name = "ID_NUMBER")
	private String idNumber;

	/** 电子邮件 */
	// @Column(unique = true)
	private String email;

	public void setActivated(boolean activated) {
		this.activated = activated;
	}




//	/** 是否激活 **/
//	private boolean activated;




	/**
	 * 手机号码
	 */
	// @Column(unique = true)
	private String mobile;

	/** 头像URL */
	@Column(name = "HEADIMGURL")
	private String headImgUrl;


	/** 单位编号 */
	@Column(name = "unit_Id")
	private Long unitId;

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

	/** 是否通过面部识别认证 */
	@NotNull
	@Column(name = "REAL_NAME_FACE")
	private boolean realNameFace = false;

	/** 绑定社保卡时间 **/
	@Column(name = "BIND_CARD_DATE")
	private String bindCardDate;

	/** 账号禁用原因 */
	@Column(name = "ACCOUNT_LOCKED_REASON")
	private String inActiveReason;

	/** CA key */
	@Column(unique = true, name = "CAKEY")
	private String caKey;

	/** 用户角色 */
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "T_USER_ROLE", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
	private Set<Role> roles = new HashSet<Role>();

	/** 用户所属组织机构 */
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "org_Id")
	private Organization organization;

	/** 企业用户关联单位 */
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "T_USER_COMPANY", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "company_id") })
	private Set<Company> companys = new HashSet<Company>();

	// /** 个人用户关联社保人员信息 */
	// @ManyToMany(fetch = FetchType.EAGER)
	// @JoinTable(name = "T_USER_PERSON", joinColumns = { @JoinColumn(name =
	// "user_id") }, inverseJoinColumns = { @JoinColumn(name = "person_id") })
	// private Set<Person> persons = new HashSet<Person>();

	// /** 单位专管员 */
	// @ManyToMany(fetch = FetchType.EAGER)
	// @JoinTable(name = "T_USER_BUZZCONTACTOR", joinColumns = {
	// @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name
	// = "buzzcontactor_user_id") })
	// private Set<User> buzzContactors = new HashSet<User>();

	/** 用户家庭成员 */
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "T_USER_FAMILY", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "family_id") })
	private Set<Family> families = new HashSet<Family>();

	/** 是否激活ohwyaa平台用户 */
	@Column(name = "OHWYAA_ACTIVED", table = "T_USER_EXTEND")
	private Boolean activatedOhwyaa = false;

	/** 主单位编号 **/
	@Column(name = "MAIN_COMPANY_NUM", table = "T_USER_EXTEND")
	private String mainCompanyNum;

	/** CA操作类型，新增0、增项1 **/
	@Column(name = "CA_OPER_TYPE", table = "T_USER_EXTEND")
	private String caOperType;

	/** CA操作时间 **/
	@Column(name = "CA_OPER_TIME", table = "T_USER_EXTEND")
	private Long caOperTime;

	/** 地区编码 */
	@Column(name = "AREA_CODE", table = "T_USER_EXTEND")
	private String areaCode;

	@Column(name = "ORG_CODE")
	private String orgCode;

	@Column(name = "EXPERT_ACCOUNT")
	private String expertAccount;



	// @Column
	// private String openid;
	//
	// public String getOpenid() {
	// return openid;
	// }
	//
	// public void setOpenid(String openid) {
	// this.openid = openid;
	// }

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public Set<Family> getFamilies() {
		return Collections.unmodifiableSet(families);
	}

	public void setFamilies(Set<Family> families) {
		this.families = families;
	}

	/**
	 * 构造函数
	 */
	protected User() {
		// for jpa
	}

	/**
	 * 构造函数
	 * 
	 * @param account
	 * @param userName
	 */
	public User(String account, String name) {
		super();
		Validate.notBlank(account, ACCOUNT_NULL_ERR);
		Validate.notBlank(name, NAME_NULL_ERR);
		this.account = account;
		this.name = name;
		// 激活用户
		this.activated = true;
		// 没有进行实名认证
		this.realNameAuthed = false;
	}

	/**
	 * 构造函数
	 * 
	 * @param account
	 * @param userName
	 * @param password
	 */
	public User(String account, String name, String password) {
		super();
		Validate.notBlank(account, ACCOUNT_NULL_ERR);
		Validate.notBlank(name, NAME_NULL_ERR);
		Validate.notBlank(password, PASSWORD_NULL_ERR);
		this.account = account;
		this.name = name;
		this.password = password;
		// 激活用户
		this.activated = true;
	}

	/**
	 * @return the secretQuestion
	 */
	public String getSecretQuestion() {
		return secretQuestion;
	}

	/**
	 * @param secretQuestion
	 *            the secretQuestion to set
	 */
	public void setSecretQuestion(String secretQuestion) {
		this.secretQuestion = secretQuestion;
	}

	/**
	 * @return the extension
	 */
	public String getExtension() {
		return extension;
	}

	/**
	 * @param extension
	 *            the extension to set
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}

	/**
	 * @return the identityVerification
	 */
	public String getIdentityVerification() {
		return identityVerification;
	}

	/**
	 * @param identityVerification
	 *            the identityVerification to set
	 */
	public void setIdentityVerification(String identityVerification) {
		this.identityVerification = identityVerification;
	}

	public String getMainCompanyNum() {
		return mainCompanyNum;
	}

	public void setMainCompanyNum(String mainCompanyNum) {
		this.mainCompanyNum = mainCompanyNum;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	/**
	 * @return the registedDate
	 */
	public String getRegistedDate() {
		return registedDate;
	}

	/**
	 * @param registedDate
	 *            the registedDate to set
	 */
	public void setRegistedDate(String registedDate) {
		this.registedDate = registedDate;
	}

	/**
	 * @return the realNameDate
	 */
	public String getRealNameDate() {
		return realNameDate;
	}

	/**
	 * @param realNameDate
	 *            the realNameDate to set
	 */
	public void setRealNameDate(String realNameDate) {
		this.realNameDate = realNameDate;
	}

	public String getCaOperType() {
		return caOperType;
	}

	public void setCaOperType(String caOperType) {
		this.caOperType = caOperType;
	}

	public Long getCaOperTime() {
		return caOperTime;
	}

	public void setCaOperTime(Long caOperTime) {
		this.caOperTime = caOperTime;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Override
	@JsonIgnoreProperties
	public String getPK() {
		return account;
	}

	public String getAccount() {
		return account;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public String getSalt() {
		return salt;
	}

	public String getEmail() {
		return email;
	}

	/**
	 * @return the activated
	 */
	public boolean isActivated() {
		return activated;
	}

	public String getCaKey() {
		return caKey;
	}

	/**
	 * 获取用户角色
	 * 
	 * @return
	 */
	public Set<Role> getRoles() {
		return Collections.unmodifiableSet(roles);
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	// /**
	// * 设置CAKey
	// *
	// * @param caKey
	// */
	// public void setCaKey(String caKey) {
	// if (!BeanHelper.isValueChange(this.caKey, caKey)) {
	// return;
	// }
	// String oldValue = this.caKey;
	// this.caKey = caKey;
	// // 发布Cakey变动事件
	// this.raiseEvent(new CaKeyChangeEvent(account, name, oldValue, caKey,
	// LongDateUtils.nowAsSecondLong()));
	//
	// }

	/**
	 * 为用户增加角色
	 * 
	 * @param role
	 */
	public void addRole(Role role) {
		Validate.notNull(role, "新增角色不能为空");
		this.roles.add(role);
	}

	/**
	 * 删除用户角色
	 * 
	 * @param role
	 */
	public void removeRole(Role role) {
		Validate.notNull(role, "要删除的角色不能为空");
		this.roles.remove(role);
	}

	/**
	 * 暂停用户访问,必须提供暂停原因
	 * 
	 * @param reason
	 */
	public void inActiveUser(String reason) {
		Validate.notNull(reason, "禁用用户原因不能为空");
		this.inActiveReason = reason;
		this.activated = false;
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

	/**
	 * 恢复用户访问
	 */
	public void activeUser() {
		this.activated = true;
	}



	/**
	 * 取消用户访问
	 */
	public void inactiveUser() {
		this.activated = false;
	}

	/**
	 * 激活ohwyaa平台账户
	 */
	public void activeOhwyaa() {
		this.activatedOhwyaa = true;
	}

	/**
	 * @param mobile
	 *            the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @return the inActiveReason
	 */
	public String getInActiveReason() {
		return inActiveReason;
	}

	/**
	 * @param salt
	 *            the salt to set
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 获取加密盐
	 * 
	 * @return
	 */
	public String getCredentialsSalt() {
		return account + salt;
	}

	/**
	 * 获取用户类型
	 * 
	 * @return
	 */
	public String getUserTypeString() {
		DiscriminatorValue discriminator = AnnotationUtils.getAnnotation(this.getClass(), DiscriminatorValue.class);
		if (discriminator != null) {
			return discriminator.value();
		} else {
			return "";
		}

	}

	/**
	 * 为用户增加关联单位
	 * 
	 * @param company
	 */
	public void addCompany(Company company) {
		Validate.notNull(company, "新增关联单位不能为空");
		this.companys.add(company);
	}

	/**
	 * 删除用户关联单位
	 * 
	 * @param company
	 */
	public void removeCompany(Company company) {
		Validate.notNull(company, "要删除的关联单位不能为空");
		this.companys.remove(company);
	}

	// public Set<Person> getPersons() {
	// return persons;
	// }
	//
	// public void setPersons(Set<Person> persons) {
	// this.persons = persons;
	// }

	/**
	 * 为个人用户增加关联社保人员信息
	 * 
	 * @param person
	 */
	// public void addPerson(Person person) {
	// Validate.notNull(person, "新增关联个人不能为空");
	// this.persons.add(person);
	// }

	/**
	 * 删除个人用户关联社保人员信息
	 * 
	 * @param person
	 */
	// public void removePerson(Person person) {
	// Validate.notNull(person, "要删除的社保人员信息不能为空");
	// this.persons.remove(person);
	// }

	/**
	 * @return the idType
	 */
	public String getIdType() {
		return idType;
	}

	/**
	 * @param idType
	 *            the idType to set
	 */
	public void setIdType(String idType) {
		this.idType = idType;
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
	 * @return the organization
	 */
	public Organization getOrganization() {
		return organization;
	}

	/**
	 * @param organization
	 *            the organization to set
	 */
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	/**
	 * @return the companys
	 */
	public Set<Company> getCompanys() {
		return companys;
	}

	/**
	 * @param companys
	 *            the companys to set
	 */
	public void setCompanys(Set<Company> companys) {
		this.companys = companys;
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
	 * @return the headImgUrl
	 */
	public String getHeadImgUrl() {
		return headImgUrl;
	}

	/**
	 * @param headImgUrl
	 *            the headImgUrl to set
	 */
	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	/**
	 * @return the buzzContactors
	 */
	// public Set<User> getBuzzContactors() {
	// return buzzContactors;
	// }

	/**
	 * @param buzzContactors
	 *            the buzzContactors to set
	 */
	// public void setBuzzContactors(Set<User> buzzContactors) {
	// this.buzzContactors = buzzContactors;
	// }

	public void addFamily(Family family) {
		Validate.notNull(family, "新增家庭成员不能为空");
		families.add(family);
	}

	public void removeFamily(Family family) {
		Validate.notNull(family, "要删除的家庭成员不能为空");
		families.remove(family);
	}

	public boolean isBindCardAuthed() {
		return bindCardAuthed;
	}

	public void setBindCardAuthed(boolean bindCardAuthed) {
		this.bindCardAuthed = bindCardAuthed;
	}

	public String getBindCardDate() {
		return bindCardDate;
	}

	public void setBindCardDate(String bindCardDate) {
		this.bindCardDate = bindCardDate;
	}

	public boolean isRealNameFace() {
		return realNameFace;
	}

	public void setRealNameFace(boolean realNameFace) {
		this.realNameFace = realNameFace;
	}

	public String getExpertAccount() {
		return expertAccount;
	}

	public void setExpertAccount(String expertAccount) {
		this.expertAccount = expertAccount;
	}


	public void setAccount(String account) {
		this.account = account;
	}

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}
}
