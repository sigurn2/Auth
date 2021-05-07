package com.neusoft.sl.si.authserver.base.domains.user;

import com.neusoft.sl.girder.ddd.hibernate.entity.UuidEntityBase;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;



@Entity
@Access(AccessType.FIELD)
@Table(name = "TEMP_USER_PERSON")
public class PersonTempUser extends UuidEntityBase<PersonTempUser, String> {


    /** 身份证 */
    @Column(name = "IDNUMBER")
    private String idNumber;

    /** 姓名 */
    @Column(name = "NAME")
    private String name;

    /** 密码 */
    @Column(name = "PASSWORD")
    private String password;

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }
    public String getMobile() {
        return "123456";
    }

    public String getUsername() {
        return "123456";
    }
    public void setPassword(String password) {
        this.password = password;
    }



    public PersonTempUser() {
    }



    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 2544115629809488873L;

    @Override
    public String toString() {
        return "PersonTempUser{" +
                "idNumber='" + idNumber + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public String getPK() {
        return null;
    }
}
