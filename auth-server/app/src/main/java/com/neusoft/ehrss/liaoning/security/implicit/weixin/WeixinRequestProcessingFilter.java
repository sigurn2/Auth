package com.neusoft.ehrss.liaoning.security.implicit.weixin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import com.neusoft.sl.si.authserver.uaa.exception.CaptchaErrorException;
import com.neusoft.sl.si.authserver.uaa.validate.login.PasswordErrorRedisManager;

public class WeixinRequestProcessingFilter extends UsernamePasswordAuthenticationFilter {

	private PasswordErrorRedisManager passwordErrorRedisManager;

	public WeixinRequestProcessingFilter(String filterProcessesUrl) {
		setFilterProcessesUrl(filterProcessesUrl);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		if (!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}
		String username = request.getParameter("username");
		String userpassword = request.getParameter("password");
		if (StringUtils.isEmpty(username)) {
			throw new CaptchaErrorException("请输入用户名");
		}
		if (StringUtils.isEmpty(userpassword)) {
			throw new CaptchaErrorException("请输入密码");
		}

		passwordErrorRedisManager.verifyPasswordErrorCount(username);

		username = username.trim();
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, userpassword);
		setDetails(request, authRequest);

		try {
			return this.getAuthenticationManager().authenticate(authRequest);
		} catch (Exception e) {
			if (e instanceof UsernameNotFoundException) {
				passwordErrorRedisManager.updatePasswordErrorCount(username);
			}
			throw e;
		}
	}

	public PasswordErrorRedisManager getPasswordErrorRedisManager() {
		return passwordErrorRedisManager;
	}

	public void setPasswordErrorRedisManager(PasswordErrorRedisManager passwordErrorRedisManager) {
		this.passwordErrorRedisManager = passwordErrorRedisManager;
	}

}
