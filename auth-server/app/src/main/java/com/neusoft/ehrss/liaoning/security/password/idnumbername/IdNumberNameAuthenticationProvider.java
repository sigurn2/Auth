package com.neusoft.ehrss.liaoning.security.password.idnumbername;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import com.neusoft.sl.si.authserver.uaa.userdetails.SaberUserDetails;

/**
 * 带验证码的 用户名密码登录验证器
 * 
 * @author mojf
 *
 */

public class IdNumberNameAuthenticationProvider extends DaoAuthenticationProvider {

	private static final Logger log = LoggerFactory.getLogger(IdNumberNameAuthenticationProvider.class);

	@Override
	public boolean supports(Class<? extends Object> authentication) {
		return (IdNumberNameAuthenticationToken.class.isAssignableFrom(authentication)) && (authentication.getSimpleName().equals(IdNumberNameAuthenticationToken.class.getSimpleName()));
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		// super.additionalAuthenticationChecks(userDetails, authentication);

		if (authentication.getCredentials() == null) {
			log.error("认证失败，没有传入姓名");
			throw new BadCredentialsException("请输入姓名");
		}

		String name = authentication.getCredentials().toString();

		if (userDetails instanceof SaberUserDetails) {
			SaberUserDetails details = (SaberUserDetails) userDetails;
			if (!name.equals(details.getName())) {
				log.error("【姓名比对失败，数据库保存的name={}，传入的name={}】", details.getName(), name);
				throw new BadCredentialsException("用户姓名有误");
			}
		} else {
			log.error("UserDetails={}", userDetails);
			throw new BadCredentialsException("认证失败，比对姓名时出错");
		}
	}

}
