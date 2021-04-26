package com.neusoft.sl.girder.security.oauth2.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

/**
 * User 数据传输对象
 * 
 * @author mojf
 *
 */
public class AuthenticatedCasUser implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -1074559970343717883L;
    /** 账户名 */
    @NotNull
    private String account;
    /** 用户名 */
    @NotNull
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
    /** 关联家庭成员 */
//    private List<FamilyQueryDTO> associatedFamilies;
    /** 角色 */
    private List<String> roles;
    /** 可访问系统 */
    private Set<AppDTO> apps;
    /** 用户类型 */
    private String userType;
    /** 头像URL */
    private String headImgUrl;
    /** 实名认证状态 */
    private Boolean hasRealNameAuth;
    /** 用户所属组织机构 */
    private OrganizationDTO organization;
    /** 主单位编号 **/
    private String mainCompanyNumber;
    /** 扩展extension**/
    private String extension;

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    /**
     * 是否激活
     */
    private Boolean actived;

    private String orgCode;

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    /**
	 * 是否通过面部识别认证
	 */
	private boolean hasRealNameFace;
    
    /** 是否绑定社保卡 **/
	private boolean hasBindCardAuth;

    public String getMainCompanyNumber() {
        return mainCompanyNumber;
    }

    public void setMainCompanyNumber(String mainCompanyNumber) {
        this.mainCompanyNumber = mainCompanyNumber;
    }

    /**
     * @return the account
     */
    public String getAccount() {
        return account;
    }

    /**
     * @param account the account to set
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
     * @param name the name to set
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
     * @param email the email to set
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
     * @param mobile the mobile to set
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
     * @param idNumber the idNumber to set
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
     * @param associatedCompanys the associatedCompanys to set
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
     * @param associatedPersons the associatedPersons to set
     */
    public void setAssociatedPersons(List<PersonDTO> associatedPersons) {
        this.associatedPersons = associatedPersons;
    }

    /**
     * @return the roles
     */
    public List<String> getRoles() {
        return roles;
    }

    /**
     * @param roles the roles to set
     */
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    /**
     * @return the apps
     */
    public Set<AppDTO> getApps() {
        return apps;
    }

    /**
     * @param apps the apps to set
     */
    public void setApps(Set<AppDTO> apps) {
        this.apps = apps;
    }

    /**
     * @return the userType
     */
    public String getUserType() {
        return userType;
    }

    /**
     * @param userType the userType to set
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     * @return the headImgUrl
     */
    public String getHeadImgUrl() {
        return headImgUrl;
    }

    /**
     * @param headImgUrl the headImgUrl to set
     */
    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    /**
     * @return the hasRealNameAuth
     */
    public Boolean getHasRealNameAuth() {
        return hasRealNameAuth;
    }

    /**
     * @param hasRealNameAuth the hasRealNameAuth to set
     */
    public void setHasRealNameAuth(Boolean hasRealNameAuth) {
        this.hasRealNameAuth = hasRealNameAuth;
    }

    /**
     * @return the organization
     */
    public OrganizationDTO getOrganization() {
        return organization;
    }

    /**
     * @param organization the organization to set
     */
    public void setOrganization(OrganizationDTO organization) {
        this.organization = organization;
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

	public boolean isHasRealNameFace() {
		return hasRealNameFace;
	}

	public void setHasRealNameFace(boolean hasRealNameFace) {
		this.hasRealNameFace = hasRealNameFace;
	}

	public boolean isHasBindCardAuth() {
		return hasBindCardAuth;
	}

	public void setHasBindCardAuth(boolean hasBindCardAuth) {
		this.hasBindCardAuth = hasBindCardAuth;
	}

//	public List<FamilyQueryDTO> getAssociatedFamilies() {
//		return associatedFamilies;
//	}
//
//	public void setAssociatedFamilies(List<FamilyQueryDTO> associatedFamilies) {
//		this.associatedFamilies = associatedFamilies;
//	}


    public Boolean getActived() {
        return actived;
    }

    public void setActived(Boolean actived) {
        this.actived = actived;
    }
}
