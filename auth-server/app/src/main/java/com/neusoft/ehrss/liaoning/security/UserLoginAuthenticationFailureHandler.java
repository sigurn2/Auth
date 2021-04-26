package com.neusoft.ehrss.liaoning.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class UserLoginAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	protected final Log logger = LogFactory.getLog(getClass());
	
	public UserLoginAuthenticationFailureHandler(){
	}
	
//		{
//	        "timestamp": 1502267742551,
//	        "status": 401,
//	        "error": "Unauthorized",
//	        "message": "Authentication Failed: 用户名密码错误",
//	        "path": "/uaa/api/newlogin/idandmobile/login"
//	    }
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		logger.debug("sending 401 Unauthorized error");
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, exception.getMessage());
	}
	
}
