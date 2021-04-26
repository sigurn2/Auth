package com.neusoft.ehrss.liaoning.security.implicit.weixin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class WeixinAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	public WeixinAuthenticationFailureHandler() {
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		logger.debug("sending 401 Unauthorized error");
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, exception.getMessage());
	}

}
