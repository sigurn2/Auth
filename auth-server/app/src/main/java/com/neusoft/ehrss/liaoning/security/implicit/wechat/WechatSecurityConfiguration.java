package com.neusoft.ehrss.liaoning.security.implicit.wechat;

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

import com.neusoft.ehrss.liaoning.provider.wx.WechatService;
import com.neusoft.ehrss.liaoning.security.AbstractSecurityConfiguration;
import com.neusoft.ehrss.liaoning.security.implicit.wechat.userdetails.WechatUserDetailsService;
import com.neusoft.sl.si.authserver.base.services.redis.WxUserRedisService;

/**
 * 微信登录安全配置
 * 
 */
@Configuration
@Order(-35)
public class WechatSecurityConfiguration extends AbstractSecurityConfiguration {

	@Autowired
	private WechatUserDetailsService userDetailsService;

	@Resource
	private DefaultTokenServices defaultAuthorizationServerTokenServices;

	private String redirectProcessesUrl = "/api/person/wechat/{type}/{pageId}";

	@Autowired
	private WechatService wechatService;

	@Autowired
	private WxUserRedisService wxUserRedisService;

	@Override
	protected UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	@Override
	protected String getFilterProcessesUrl() {
		return "/api/person/wechat/*/*";
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
		return new String[] { "/api/person/wechat/*/*" };
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
				// .formLogin()//
				// .loginPage("/personlogin")//
				// .permitAll()//
				// .and()//
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
		WechatAuthenticationProvider provider = new WechatAuthenticationProvider();
		provider.setUserDetailsService(getUserDetailsService());
		provider.setHideUserNotFoundExceptions(false);
		return provider;
	}

	@Override
	protected Filter getAuthenticationProcessingFilter() throws Exception {
		WechatRequestProcessingFilter filter = new WechatRequestProcessingFilter(getFilterProcessesUrl());
		filter.setAuthenticationManager(authenticationManager());

		WechatAuthenticationSuccessHandler successHandler = new WechatAuthenticationSuccessHandler();
		successHandler.setDefaultAuthorizationServerTokenServices(defaultAuthorizationServerTokenServices);
		successHandler.setRedirectProcessesUrl(redirectProcessesUrl);
		successHandler.setWxUserRedisService(wxUserRedisService);
		filter.setAuthenticationSuccessHandler(successHandler);

		WechatAuthenticationFailureHandler failureHandler = new WechatAuthenticationFailureHandler();
		failureHandler.setRedirectProcessesUrl(redirectProcessesUrl);
		failureHandler.setWxUserRedisService(wxUserRedisService);
		filter.setAuthenticationFailureHandler(failureHandler);
		
		filter.setRedirectProcessesUrl(redirectProcessesUrl);
		filter.setWechatService(wechatService);
		filter.setWxUserRedisService(wxUserRedisService);
		return filter;
	}

}
