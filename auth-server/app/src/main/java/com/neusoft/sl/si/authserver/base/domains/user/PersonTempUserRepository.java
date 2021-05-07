package com.neusoft.sl.si.authserver.base.domains.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PersonTempUserRepository extends JpaRepository<PersonTempUser, String>, JpaSpecificationExecutor<PersonTempUser> {

    /**
     * 按身份证查找
     *
     * @param idNumber
     * @return
     */
    PersonTempUser findByIdNumber(String idNumber);


}