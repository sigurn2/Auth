package com.neusoft.sl.si.authserver.base.domains.user;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.neusoft.sl.si.authserver.base.domains.user.enums.UserType;

/**
 * 个人用户
 * 
 * @author mojf
 * 
 */
@Entity
@DiscriminatorValue(UserType.USER_EXPERT)
public class ExpertUser extends User {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    protected ExpertUser() {
        super();
    }

    public ExpertUser(String account, String name, String password) {
        super(account, name, password);
    }

}
