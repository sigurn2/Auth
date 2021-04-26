package com.neusoft.ehrss.liaoning.security.person;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;

import com.neusoft.ehrss.liaoning.provider.ecard.EcardService;
import com.neusoft.ehrss.liaoning.security.AbstractSecurityConfiguration;
import com.neusoft.sl.si.authserver.uaa.userdetails.person.PersonUserDetailService;

/**
 * 个人网厅登录SecurityConfiguration
 * 
 */
@Configuration
@Order(-39)
public class QRCodeSecurityConfiguration extends AbstractSecurityConfiguration {

	@Autowired
	private PersonUserDetailService userDetailsService;

	@Autowired
	private EcardService ecardService;

	@Value("${saber.auth.person.url}")
	private String personUrl = "";

	@Override
	protected UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	@Override
	protected String getFilterProcessesUrl() {
		return "/api/person/qrcode/login";
	}

	@Override
	protected String getTargetUrl() {
		return personUrl;
	}

	@Override
	protected String getLoginFormUrl() {
		return "/personlogin/";
	}

	@Override
	protected String[] getAntMatchers() {
		return new String[] { "/api/person/qrcode/**" };
	}

	@Override
	protected AuthenticationProvider getAuthenticationProvider() {
		QRCodeAuthenticationProvider provider = new QRCodeAuthenticationProvider();
		provider.setUserDetailsService(getUserDetailsService());
		provider.setHideUserNotFoundExceptions(false);
		return provider;
	}

	protected Filter getAuthenticationProcessingFilter() throws Exception {
		QRCodeRequestProcessingFilter filter = new QRCodeRequestProcessingFilter(getFilterProcessesUrl());
		filter.setAuthenticationManager(authenticationManager());
		filter.setSessionAuthenticationStrategy(new SessionFixationProtectionStrategy());
		filter.setAuthenticationSuccessHandler(getSuccessHandler());
		filter.setAuthenticationFailureHandler(getFailureHandler());
		filter.setEcardService(ecardService);
		return filter;
	}

	@Override
	protected SimpleUrlAuthenticationFailureHandler getFailureHandler() {
		return new SimpleUrlAuthenticationFailureHandler("/error");
	}

	@Override
	protected SimpleUrlAuthenticationSuccessHandler getSuccessHandler() {
		QRCodeAuthenticationSuccessHandler successHandler = new QRCodeAuthenticationSuccessHandler(getTargetUrl());
		successHandler.setAlwaysUseDefaultTargetUrl(false);
		return successHandler;
	}

}
