package com.neusoft.sl.si.authserver.password;

import com.neusoft.sl.si.authserver.base.services.password.PasswordEncoderService;
import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import javax.annotation.Resource;

/**
 * @program: liaoning-auth
 * @description: 密码校验 接口 ，多地市接入提供多地市校验方式
 * @author: lgy
 * @create: 2020-06-19 18:13
 **/

public interface CheckPwdService {



    default boolean match(String password,String tempPwd){
        return encrypt(password).equals(tempPwd);
    }
    default boolean matchbenxi(String password,String tempPwd){
        return password.equals(tempPwd);
    }



    default String encrypt(String password){
        return STPwdEncrypt.md5Encrypt32Lower(password);
    }



}
