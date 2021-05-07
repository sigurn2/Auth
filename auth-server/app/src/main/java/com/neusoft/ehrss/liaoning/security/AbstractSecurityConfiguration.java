package com.neusoft.ehrss.liaoning.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.Filter;

import com.neusoft.sl.si.authserver.base.domains.user.PersonTempUserRepository;
import com.neusoft.sl.si.authserver.base.domains.user.TempUserRepository;
import com.neusoft.sl.si.authserver.base.domains.user.ThinUserRepository;
import com.neusoft.sl.si.authserver.base.services.user.UserCustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import com.neusoft.sl.si.authserver.base.services.password.PasswordEncoderService;
import com.neusoft.sl.si.authserver.uaa.filter.CorsFilter;
import com.neusoft.sl.si.authserver.uaa.filter.captcha.CaptchaRequestService;
import com.neusoft.sl.si.authserver.uaa.filter.captcha.provider.UserPasswordWithImgCaptchaAuthenticationProvider;
import com.neusoft.sl.si.authserver.uaa.validate.login.PasswordErrorRedisManager;

/**
 * 抽象出来的SecurityConfiguration
 *
 */
public abstract class AbstractSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Resource(name = "${saber.auth.password.encoder}")
	private PasswordEncoderService passwordEncoderService;

	@Value("${saber.auth.cors.allowed.uris}")
	private String corsAllowedUris = "";

	@Value("${saber.auth.cors.allowed.origins}")
	private String corsAllowedOrigins = "";

	@Value("#{'${cors.xhr.allowed.x-requested-with:^$}'.split(',')}")
	private List<String> corsXhrAllowedXRequestedWiths = new ArrayList<String>();

	@Autowired
	private CaptchaRequestService captchaRequestService;

	@Autowired
	private TempUserRepository tempUserRepository;

	@Autowired
	private PersonTempUserRepository personTempUserRepository;
	@Autowired
	private ThinUserRepository userRepository;

	@Autowired
	private PasswordErrorRedisManager passwordErrorRedisManager;

	@Autowired
	private UserCustomService userCustomService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(getAuthenticationProvider());
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
				// .csrf().disable()
				.csrf().csrfTokenRepository(csrfTokenRepository())//
				.and().httpBasic().disable();
		// @formatter:on
	}

	protected CsrfTokenRepository csrfTokenRepository() {
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setHeaderName("X-XSRF-TOKEN");
		return repository;
	}

	protected abstract UserDetailsService getUserDetailsService();

	protected abstract String getFilterProcessesUrl();

	protected abstract String getTargetUrl();

	protected abstract String getLoginFormUrl();

	protected abstract String[] getAntMatchers();

	protected SimpleUrlAuthenticationSuccessHandler getSuccessHandler() {
		UserLoginAuthenticationSuccessHandler successHandler = new UserLoginAuthenticationSuccessHandler(getTargetUrl());
		successHandler.setAlwaysUseDefaultTargetUrl(false);
		return successHandler;
	}

	protected SimpleUrlAuthenticationFailureHandler getFailureHandler() {
		return new UserLoginAuthenticationFailureHandler();
	}

	protected AuthenticationProvider getAuthenticationProvider() {
		UserPasswordWithImgCaptchaAuthenticationProvider provider = new UserPasswordWithImgCaptchaAuthenticationProvider();
		provider.setPasswordEncoder(getPasswordEncoderService().build());
		provider.setUserDetailsService(getUserDetailsService());
		provider.setHideUserNotFoundExceptions(false);
		provider.setPasswordErrorRedisManager(passwordErrorRedisManager);
		return provider;
	}

	protected Filter getAuthenticationProcessingFilter() throws Exception {
		UserLoginWithImgCaptchaRequestProcessingFilter filter = new UserLoginWithImgCaptchaRequestProcessingFilter(getFilterProcessesUrl());
		filter.setAuthenticationManager(authenticationManager());
		filter.setSessionAuthenticationStrategy(new SessionFixationProtectionStrategy());
		filter.setAuthenticationSuccessHandler(getSuccessHandler());
		filter.setAuthenticationFailureHandler(getFailureHandler());
		filter.setPasswordErrorRedisManager(passwordErrorRedisManager);
		filter.setCaptchaRequestService(captchaRequestService);
		filter.setPersonTempUserRepository(personTempUserRepository);
		filter.setTempUserRepository(tempUserRepository);
		filter.setThinUserRepository(userRepository);
		filter.setUserCustomService(userCustomService);
		return filter;
	}

	protected CorsFilter getCorsFilter() {
		CorsFilter corsFilter = new CorsFilter();
		List<String> allowedUris = new ArrayList<String>(Arrays.asList(corsAllowedUris.split(",")));
		// 授权回调域
		List<String> allowedOrigins = new ArrayList<String>(Arrays.asList(corsAllowedOrigins.split(",")));
		corsFilter.setCorsXhrAllowedUris(allowedUris);
		corsFilter.setCorsXhrAllowedOrigins(allowedOrigins);
		corsFilter.setCorsXhrAllowedXRequestedWiths(corsXhrAllowedXRequestedWiths);
		corsFilter.initialize();
		return corsFilter;
	}

	public PasswordEncoderService getPasswordEncoderService() {
		return passwordEncoderService;
	}

	public void setPasswordEncoderService(PasswordEncoderService passwordEncoderService) {
		this.passwordEncoderService = passwordEncoderService;
	}

	public CaptchaRequestService getCaptchaRequestService() {
		return captchaRequestService;
	}

	public void setCaptchaRequestService(CaptchaRequestService captchaRequestService) {
		this.captchaRequestService = captchaRequestService;
	}

	public PasswordErrorRedisManager getPasswordErrorRedisManager() {
		return passwordErrorRedisManager;
	}

	public void setPasswordErrorRedisManager(PasswordErrorRedisManager passwordErrorRedisManager) {
		this.passwordErrorRedisManager = passwordErrorRedisManager;
	}

}
