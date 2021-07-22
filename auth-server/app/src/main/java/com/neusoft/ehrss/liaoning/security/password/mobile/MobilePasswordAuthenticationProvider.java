package com.neusoft.ehrss.liaoning.security.password.mobile;

import com.neusoft.sl.si.authserver.base.services.user.UserService;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import com.neusoft.sl.si.authserver.uaa.validate.login.PasswordErrorRedisManager;


/**
 * 带验证码的 用户名密码登录验证器
 *
 * @author mojf
 */

public class MobilePasswordAuthenticationProvider extends DaoAuthenticationProvider {

    private PasswordErrorRedisManager passwordErrorRedisManager;


    //政务服务地址uri
    private UserService userService;

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public PasswordErrorRedisManager getPasswordErrorRedisManager() {
        return passwordErrorRedisManager;
    }

    public void setPasswordErrorRedisManager(PasswordErrorRedisManager passwordErrorRedisManager) {
        this.passwordErrorRedisManager = passwordErrorRedisManager;
    }

    @Override
    public boolean supports(Class<? extends Object> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication)) && (authentication.getSimpleName().equals(UsernamePasswordAuthenticationToken.class.getSimpleName()));
    }

    @Override
    public void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        try {
            super.additionalAuthenticationChecks(userDetails, authentication);
            // 验证通过删除
            passwordErrorRedisManager.removePasswordErrorCount(userDetails.getUsername());
        } catch (Exception ex) {
            // 密码验证失败，增加次数
            UserDetails details = (UserDetails) authentication.getDetails();
            passwordErrorRedisManager.updatePasswordErrorCount(details.getPassword(), userDetails.getUsername());
             throw new InternalAuthenticationServiceException("用户名或密码错误");
        }


    }

}
