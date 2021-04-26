package com.neusoft.ehrss.liaoning.security.implicit.weixin;

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
import com.neusoft.ehrss.liaoning.security.implicit.weixin.userdetailsservice.WeixinUserDetailsService;

/**
 * 微信订阅号登录安全配置
 * 
 */
@Configuration
@Order(-37)
public class WeixinSecurityConfiguration extends AbstractSecurityConfiguration {

	@Autowired
	private WeixinUserDetailsService userDetailsService;

	@Resource
	private DefaultTokenServices defaultAuthorizationServerTokenServices;

	@Override
	protected UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	@Override
	protected String getFilterProcessesUrl() {
		return "/api/person/weixin/login";
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
		return new String[] { "/api/person/weixin/**" };
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
		WeixinAuthenticationProvider provider = new WeixinAuthenticationProvider();
		provider.setPasswordEncoder(getPasswordEncoderService().build());
		provider.setUserDetailsService(getUserDetailsService());
		provider.setHideUserNotFoundExceptions(false);
		provider.setPasswordErrorRedisManager(getPasswordErrorRedisManager());
		return provider;
	}

	@Override
	protected Filter getAuthenticationProcessingFilter() throws Exception {
		WeixinRequestProcessingFilter filter = new WeixinRequestProcessingFilter(getFilterProcessesUrl());
		filter.setAuthenticationManager(authenticationManager());

		WeixinAuthenticationSuccessHandler successHandler = new WeixinAuthenticationSuccessHandler();
		successHandler.setDefaultAuthorizationServerTokenServices(defaultAuthorizationServerTokenServices);
		filter.setAuthenticationSuccessHandler(successHandler);

		WeixinAuthenticationFailureHandler failureHandler = new WeixinAuthenticationFailureHandler();

		filter.setAuthenticationFailureHandler(failureHandler);
		filter.setPasswordErrorRedisManager(getPasswordErrorRedisManager());
		return filter;
	}

}
