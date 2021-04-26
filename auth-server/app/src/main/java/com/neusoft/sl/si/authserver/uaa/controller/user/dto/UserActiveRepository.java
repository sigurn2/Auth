package com.neusoft.sl.si.authserver.uaa.controller.user.dto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @program: liaoning-auth
 * @description:
 * @author: lgy
 * @create: 2020-02-21 14:59
 **/

public interface UserActiveRepository extends JpaRepository<UserActive, String>,JpaSpecificationExecutor<UserActive> {


    //public String findByActiveCode(String activeCode);


    public UserActive findByOrgCodeAndAreaCode(String orgCode, String areaCode);

}
