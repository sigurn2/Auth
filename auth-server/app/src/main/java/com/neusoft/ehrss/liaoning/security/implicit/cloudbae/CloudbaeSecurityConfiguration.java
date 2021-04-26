package com.neusoft.ehrss.liaoning.security.implicit.cloudbae;

import javax.annotation.Resource;
import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;

import com.neusoft.ehrss.liaoning.security.AbstractSecurityConfiguration;
import com.neusoft.ehrss.liaoning.security.implicit.cloudbae.userdetails.CloudbaeUserDetailsService;
import com.neusoft.sl.si.authserver.base.domains.user.ThinUserRepository;

/**
 * 云宝宝用户登录安全配置
 * 
 */
@Configuration
@Order(-32)
public class CloudbaeSecurityConfiguration extends AbstractSecurityConfiguration {

	@Autowired
	private CloudbaeUserDetailsService userDetailsService;

	@Resource
	private DefaultTokenServices defaultAuthorizationServerTokenServices;

	@Autowired
	private ThinUserRepository thinUserRepository;

	@Override
	protected UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	@Override
	protected String getFilterProcessesUrl() {
		return "/api/third/login";
	}

	@Override
	protected String getTargetUrl() {
		return "";
	}

	@Override
	protected String getLoginFormUrl() {
		return "/error";
	}

	@Override
	protected String[] getAntMatchers() {
		return new String[] { "/api/third/login" };
	}

	/**
	 * 配置HttpSecurity
	 */
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http.headers()//
				.contentTypeOptions().and()// X-Content-Type-Options
				.xssProtection().and()// X-XSS-Protection
				.frameOptions().and()// X-Frame-Options
				.httpStrictTransportSecurity().and()// Strict-Transport-Security
				// .contentSecurityPolicy("default-src 'self';style-src 'self'
				// 'unsafe-inline';").and()// Content-Security-Policy
				.cacheControl().and()//
				.and()//
				.requestMatchers()//
				.antMatchers(getAntMatchers())//
				.and()//
				.authorizeRequests()//
				.anyRequest()//
				.authenticated()//
				.and()//
				// login
				.formLogin()//
				.loginPage(getLoginFormUrl())//
				.permitAll()//
				.and()//
				.addFilterBefore(getCorsFilter(), WebAsyncManagerIntegrationFilter.class)//
				.addFilterBefore(getAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class)//
				.exceptionHandling()//
				.and()//
				// csrf
				.csrf().disable()
				// .csrf().csrfTokenRepository(csrfTokenRepository())//
				.httpBasic().disable();
		// @formatter:on
	}

	@Override
	protected AuthenticationProvider getAuthenticationProvider() {
		CloudbaeAuthenticationProvider provider = new CloudbaeAuthenticationProvider();
		provider.setUserDetailsService(getUserDetailsService());
		provider.setHideUserNotFoundExceptions(false);
		return provider;
	}

	@Override
	protected Filter getAuthenticationProcessingFilter() throws Exception {
		CloudbaeRequestProcessingFilter filter = new CloudbaeRequestProcessingFilter(getFilterProcessesUrl());
		filter.setAuthenticationManager(authenticationManager());
		CloudbaeAuthenticationSuccessHandler successHandler = new CloudbaeAuthenticationSuccessHandler();
		successHandler.setDefaultAuthorizationServerTokenServices(defaultAuthorizationServerTokenServices);
		successHandler.setThinUserRepository(thinUserRepository);
		filter.setAuthenticationSuccessHandler(successHandler);
		filter.setAuthenticationFailureHandler(new CloudbaeAuthenticationFailureHandler());
		return filter;
	}

}
