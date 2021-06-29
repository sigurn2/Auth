package com.neusoft.sl.si.authserver.base.domains.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @program: liaoning-auth
 * @description: 临时用户仓储
 * @author: lgy
 * @create: 2020-06-19 18:45
 **/

public interface TempUserRepository extends JpaRepository<TempUser, String>, JpaSpecificationExecutor<TempUser> {

    /**
     * 按账号以及行政区划查找用户
     *
     * @param username
     * @return
     */
    TempUser findByUsername(String username);

  //  List<TempUser> findByUsername(String username);


}

