package com.neusoft.sl.si.authserver.uaa.filter.captcha.provider;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.neusoft.sl.si.authserver.uaa.filter.captcha.token.UserPasswordWithImgCaptchaAuthenticationToken;
import com.neusoft.sl.si.authserver.uaa.validate.login.PasswordErrorRedisManager;

/**
 * 带验证码的 用户名密码登录验证器
 * 
 * @author mojf
 *
 */

public class UserPasswordWithImgCaptchaAuthenticationProvider extends DaoAuthenticationProvider {

	private PasswordErrorRedisManager passwordErrorRedisManager;

	@Override
	public boolean supports(Class<? extends Object> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication)) || (UserPasswordWithImgCaptchaAuthenticationToken.class.isAssignableFrom(authentication));
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		try {
			super.additionalAuthenticationChecks(userDetails, authentication);
			// 验证通过删除
			if (authentication.getDetails() instanceof WebAuthenticationDetails) {
				WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
				passwordErrorRedisManager.removePasswordErrorCount(details.getSessionId());
			}
			passwordErrorRedisManager.removePasswordErrorCount(userDetails.getUsername());
		} catch (Exception ex) {
			// 密码验证失败，增加次数
			WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
			passwordErrorRedisManager.updatePasswordErrorCount(details.getSessionId(), userDetails.getUsername());
			// throw new InternalAuthenticationServiceException("用户名或密码错误");
		}
	}

	public PasswordErrorRedisManager getPasswordErrorRedisManager() {
		return passwordErrorRedisManager;
	}

	public void setPasswordErrorRedisManager(PasswordErrorRedisManager passwordErrorRedisManager) {
		this.passwordErrorRedisManager = passwordErrorRedisManager;
	}

}
