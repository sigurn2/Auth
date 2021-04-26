package com.neusoft.ehrss.liaoning.security.ca;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;

import com.neusoft.ehrss.liaoning.provider.ca.DFCA.DFCAGatewayService;
import com.neusoft.ehrss.liaoning.provider.ca.GXCA.GXCAGatewayService;
import com.neusoft.ehrss.liaoning.security.AbstractSecurityConfiguration;
import com.neusoft.ehrss.liaoning.security.UserLoginAuthenticationSuccessHandler;
import com.neusoft.ehrss.liaoning.security.UserLoginUrlAuthenticationEntryPoint;
import com.neusoft.sl.si.authserver.uaa.userdetails.company.EnterpriseCAUserDetailService;

/**
 * 企业用户CA登录安全配置
 * 
 */
@Configuration
@Order(-31)
public class CASecurityConfiguration extends AbstractSecurityConfiguration {

	@Autowired
	private EnterpriseCAUserDetailService userDetailsService;

	@Autowired(required = false)
	private DFCAGatewayService dfcaGatewayService;

	@Autowired(required = false)
	private GXCAGatewayService gxcaGatewayService;

	@Value("${saber.auth.ca.url}")
	private String caUrl = "";

	@Value("${saber.auth.renshi.url}")
	private String renshiUrl = "";

	@Override
	protected UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	@Override
	protected String getFilterProcessesUrl() {
		return "/api/ca/login";
	}

	@Override
	protected String getTargetUrl() {
		return caUrl;
	}

	@Override
	protected String getLoginFormUrl() {
		return "/calogin/";
	}

	@Override
	protected String[] getAntMatchers() {
		return new String[] { "/api/ca/**" };
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
				.authenticationEntryPoint(new UserLoginUrlAuthenticationEntryPoint(getLoginFormUrl()))//
				.and()//
				// csrf
				.csrf().disable()//
				.httpBasic().disable();
	}

	@Override
	protected AuthenticationProvider getAuthenticationProvider() {
		CALoginAuthenticationProvider provider = new CALoginAuthenticationProvider();
		provider.setUserDetailsService(getUserDetailsService());
		provider.setHideUserNotFoundExceptions(false);
		return provider;
	}

	@Override
	protected Filter getAuthenticationProcessingFilter() throws Exception {
		CALoginAuthenticationProcessingFilter filter = new CALoginAuthenticationProcessingFilter(getFilterProcessesUrl(), dfcaGatewayService, gxcaGatewayService);
		filter.setAuthenticationManager(authenticationManager());
		filter.setSessionAuthenticationStrategy(new SessionFixationProtectionStrategy());
		filter.setAuthenticationSuccessHandler(getSuccessHandler());
		filter.setAuthenticationFailureHandler(getFailureHandler());
		return filter;
	}

	@Override
	protected SimpleUrlAuthenticationSuccessHandler getSuccessHandler() {
		UserLoginAuthenticationSuccessHandler successHandler = new UserLoginAuthenticationSuccessHandler(getTargetUrl());
		successHandler.setAlwaysUseDefaultTargetUrl(false);
		successHandler.setRenshiUrl(renshiUrl);
		return successHandler;
	}

}
