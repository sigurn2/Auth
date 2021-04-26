package com.neusoft.ehrss.liaoning.security.password.atm;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 带验证码的 用户名密码登录验证器
 * 
 * @author mojf
 *
 */

public class AtmAuthenticationProvider extends DaoAuthenticationProvider {

	@Override
	public boolean supports(Class<? extends Object> authentication) {
		return (authentication.getSimpleName().equals(AtmAuthenticationToken.class.getSimpleName()));
	}

	@Override
	public void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		// 无校验密码
	}

}
