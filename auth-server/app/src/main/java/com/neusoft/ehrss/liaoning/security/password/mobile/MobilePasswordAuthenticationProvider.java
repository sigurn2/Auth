package com.neusoft.ehrss.liaoning.security.password.mobile;

import com.neusoft.sl.si.authserver.base.services.user.UserService;
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

    @SuppressWarnings("deprecation")
    @Override
    public void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
//        passwordErrorRedisManager.verifyPasswordErrorCount(userDetails.getUsername());
//        Object salt = null;
//        if (userService.findByAccount(userDetails.getUsername()) != null){
//
//		}
//
//		if (super.getSaltSource() != null) {
//			salt = super.getSaltSource().getSalt(userDetails);
//		}
//
//		if (authentication.getCredentials() == null) {
//			logger.debug("Authentication failed: no credentials provided");
//			throw new BadCredentialsException("请输入密码");
//		}
//
//		String presentedPassword = authentication.getCredentials().toString();
//
//		if (!super.getPasswordEncoder().isPasswordValid(userDetails.getPassword(), presentedPassword, salt)) {
//			logger.debug("Authentication failed: password does not match stored value");
//			passwordErrorRedisManager.updatePasswordErrorCount(userDetails.getUsername());
//			// throw new BadCredentialsException("用户名或密码错误");
//		}

            passwordErrorRedisManager.removePasswordErrorCount(userDetails.getUsername());
    }

}
