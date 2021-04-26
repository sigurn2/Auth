package com.neusoft.ehrss.liaoning.security.implicit.alipay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

public class AlipayRequestProcessingFilter extends UsernamePasswordAuthenticationFilter {

	private static final Logger log = LoggerFactory.getLogger(AlipayRequestProcessingFilter.class);

	public AlipayRequestProcessingFilter(String filterProcessesUrl) {
		setFilterProcessesUrl(filterProcessesUrl);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		if (!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}

		String code = request.getParameter("code");
		if (StringUtils.isEmpty(code))
			throw new BadCredentialsException("授权码为空");
		log.debug("授权码接入code={}", code);

		String username = code;
		// String username = "36695468";

		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, "");

		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);

		return this.getAuthenticationManager().authenticate(authRequest);
	}

}
