package com.neusoft.ehrss.liaoning.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.PortResolver;
import org.springframework.security.web.PortResolverImpl;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.RedirectUrlBuilder;
import org.springframework.security.web.util.UrlUtils;

public class UserLoginUrlAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

	private static final Logger log = LoggerFactory.getLogger(UserLoginUrlAuthenticationEntryPoint.class);

	PortResolver portResolver = new PortResolverImpl();

	public UserLoginUrlAuthenticationEntryPoint(String loginFormUrl) {
		super(loginFormUrl);
	}

	@Override
	protected String determineUrlToUseForThisRequest(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
		String loginForm = getLoginFormUrl();
		if (UrlUtils.isAbsoluteUrl(loginForm)) {
			return loginForm;
		}
		int serverPort = portResolver.getServerPort(request);
		String scheme = request.getScheme();
		RedirectUrlBuilder urlBuilder = new RedirectUrlBuilder();
		urlBuilder.setScheme(scheme);
		urlBuilder.setServerName(request.getServerName());
		urlBuilder.setPort(serverPort);
		// urlBuilder.setContextPath(request.getContextPath());
		urlBuilder.setPathInfo(loginForm);
		String redirect = urlBuilder.getUrl();
		log.debug("Redirecting to '{}'", redirect);
		return redirect;
	}

}
