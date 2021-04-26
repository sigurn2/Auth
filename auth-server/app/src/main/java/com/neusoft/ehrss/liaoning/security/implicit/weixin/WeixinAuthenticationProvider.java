package com.neusoft.ehrss.liaoning.security.implicit.weixin;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import com.neusoft.sl.si.authserver.uaa.validate.login.PasswordErrorRedisManager;

public class WeixinAuthenticationProvider extends DaoAuthenticationProvider {

	private PasswordErrorRedisManager passwordErrorRedisManager;

	@Override
	public boolean supports(Class<? extends Object> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		try {
			super.additionalAuthenticationChecks(userDetails, authentication);
			// 验证通过删除
			passwordErrorRedisManager.removePasswordErrorCount(userDetails.getUsername());
		} catch (Exception ex) {
			// 密码验证失败，增加次数
			passwordErrorRedisManager.updatePasswordErrorCount(userDetails.getUsername());
			// throw new BadCredentialsException("用户名或密码错误");
		}
	}

	public PasswordErrorRedisManager getPasswordErrorRedisManager() {
		return passwordErrorRedisManager;
	}

	public void setPasswordErrorRedisManager(PasswordErrorRedisManager passwordErrorRedisManager) {
		this.passwordErrorRedisManager = passwordErrorRedisManager;
	}

}
