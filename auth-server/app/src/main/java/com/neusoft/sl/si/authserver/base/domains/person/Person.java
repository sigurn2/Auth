package com.neusoft.sl.si.authserver.base.domains.person;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;

import org.apache.commons.lang3.Validate;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.neusoft.sl.girder.ddd.hibernate.entity.EntityBase;
import com.neusoft.sl.girder.ddd.hibernate.enums.GenericEnumUserType;
import com.neusoft.sl.si.authserver.base.domains.person.identification.Certificate;

/**
 * 表示现实当中的一个人员对象
 * 
 * <pre>
 * 表示与中心端接口对象
 * </pre>
 * 
 * @author wuyf
 * 
 */
@TypeDefs({ @TypeDef(name = "stringEnum", typeClass = GenericEnumUserType.class) })
@Entity
// AccessType.FIELD标注实体以后，实体的注解需要写到 field上，不能写到get方法上
// 影响所有关联的@Embeddable对象，即关联对象不需要再标注@Access(AccessType.FIELD)
@Access(AccessType.FIELD)
public class Person extends EntityBase<Person, PersonNumber> {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 4701427750433847413L;

    /*** 个人编号 */
    @Column(name = "PERSONNUMBER")
    private PersonNumber personNumber;

    /*** 姓名 */
    private String name;

    /** 身份证件 */
    private Certificate certificate;
    /** 基本信息有效状态 **/
    @Column(name = "PROFILE_STATUS")
    private String profileStatus;

    public String getProfileStatus() {
        return profileStatus;
    }

    public void setProfileStatus(String profileStatus) {
        this.profileStatus = profileStatus;
    }

    /**
     * 获取个人编号
     * 
     * @return
     */
    public PersonNumber getPersonNumber() {
        return personNumber;
    }

    /**
     * 获取人员姓名
     * 
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 获取身份证件
     * 
     * @return
     */

    public Certificate getCertificate() {
        return certificate;
    }

    /**
     * 修改名字
     * 
     * @param name
     */
    public void setName(String name) {
        Validate.notBlank(name, "人员姓名不能为空");
        this.name = name;
    }

    /**
     * 修改证件类型
     * 
     * @param certificate
     */

    public void setCertificate(Certificate certificate) {
        Validate.notNull(certificate, "证件类型不能为空");
        this.certificate = certificate;
    }

    /**
     * 隐藏构造函数
     * 
     * @param validateFlag
     */
    Person() {
        // for hibernate
    }

    /**
     * 要创建一个人员基本档案，必须分配一个个人编号，姓名不能为空 ,身份证件不能为空
     * 
     * @param personNumber
     *            个人编号
     * @param name
     *            姓名
     */
    public Person(final PersonNumber personNumber, final String name) {
        Validate.notNull(personNumber, "人员编号不能为空");
        this.personNumber = personNumber;
        this.setName(name);
    }
    
    /**
     * 离退休状态
     */
    @Column(name = "RETIRE_STATUS")
    private String retireStatus;

    public String getRetireStatus() {
		return retireStatus;
	}

	public void setRetireStatus(String retireStatus) {
		this.retireStatus = retireStatus;
	}
	
	@Column(name = "SOCIAL_SECURITY_CARD_NUMBER")
    private String socialSecurityCardNumber;

    public String getSocialSecurityCardNumber() {
		return socialSecurityCardNumber;
	}

	public void setSocialSecurityCardNumber(String socialSecurityCardNumber) {
		this.socialSecurityCardNumber = socialSecurityCardNumber;
	}

	/**
     * 获取个人管理编号
     * 
     * @return
     */
    public String getPersonNumberString() {
        return personNumber.getNumber();
    }

    /**
     * 获取业务主键
     */
    public PersonNumber getPK() {
        return this.personNumber;
    }

}
