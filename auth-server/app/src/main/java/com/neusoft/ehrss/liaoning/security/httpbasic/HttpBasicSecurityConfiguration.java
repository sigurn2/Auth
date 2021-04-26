package com.neusoft.ehrss.liaoning.security.httpbasic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.ClientDetailsUserDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;

import com.neusoft.sl.si.authserver.uaa.filter.CorsFilter;

/**
 * HttpBasic SecurityConfiguration
 *
 */
@Configuration
@Order(-20)
public class HttpBasicSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Value("${saber.auth.cors.allowed.uris}")
	private String corsAllowedUris = "";

	@Value("${saber.auth.cors.allowed.origins}")
	private String corsAllowedOrigins = "";

	@Value("#{'${cors.xhr.allowed.x-requested-with:^$}'.split(',')}")
	private List<String> corsXhrAllowedXRequestedWiths = new ArrayList<String>();

	@Autowired
	private DataSource dataSource;

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
				.antMatchers("/password/reset/token", "/wechat/menu/**","/wechat/media/**")//
				.and()//
				.authorizeRequests()//
				.anyRequest()//
				.authenticated()//
				.and()//
				.addFilterBefore(getCorsFilter(), WebAsyncManagerIntegrationFilter.class)//
				// csrf
				.csrf().disable()//
				.httpBasic();
		// @formatter:on
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

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(getAuthenticationProvider());
	}

	protected AuthenticationProvider getAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		ClientDetailsUserDetailsService clientDetailsUserDetailsService = new ClientDetailsUserDetailsService(clientDetailsService());
		provider.setUserDetailsService(clientDetailsUserDetailsService);
		provider.setHideUserNotFoundExceptions(false);
		return provider;
	}

	private ClientDetailsService clientDetailsService() {
		return new JdbcClientDetailsService(dataSource);
	}

}
