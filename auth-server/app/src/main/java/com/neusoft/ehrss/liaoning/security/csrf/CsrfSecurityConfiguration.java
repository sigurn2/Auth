package com.neusoft.ehrss.liaoning.security.csrf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import com.neusoft.sl.si.authserver.uaa.filter.CorsFilter;

/**
 * 只用于获取csrf token，把token放在cookie中。
 * 
 * @author zhou.haidong
 */
@Configuration
@Order(-21)
public class CsrfSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Value("${saber.auth.cors.allowed.uris}")
	private String corsAllowedUris = "";

	@Value("${saber.auth.cors.allowed.origins}")
	private String corsAllowedOrigins = "";

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.headers()//
				.contentTypeOptions().and()// X-Content-Type-Options
				.xssProtection().and()// X-XSS-Protection
				.frameOptions().and()// X-Frame-Options
				.httpStrictTransportSecurity().and()// Strict-Transport-Security
//				.contentSecurityPolicy("default-src 'self';style-src 'self' 'unsafe-inline';").and()// Content-Security-Policy
				.cacheControl().and()//
				.and()//
				.requestMatchers()//
				.antMatchers("/prevent/token")//
				.and()//
				.addFilterBefore(getCorsFilter(), WebAsyncManagerIntegrationFilter.class)//
				.exceptionHandling()//
				.and()//
				.csrf().csrfTokenRepository(csrfTokenRepository()).and()//
				.addFilterAfter(new CsrfTokenFilter(), CsrfFilter.class)//
				.httpBasic().disable();
	}

	private CsrfTokenRepository csrfTokenRepository() {
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setHeaderName("X-XSRF-TOKEN");
		return repository;
	}

	public CorsFilter getCorsFilter() {
		CorsFilter corsFilter = new CorsFilter();
		List<String> allowedUris = new ArrayList<String>(Arrays.asList(corsAllowedUris.split(",")));
		// 授权回调域
		List<String> allowedOrigins = new ArrayList<String>(Arrays.asList(corsAllowedOrigins.split(",")));
		corsFilter.setCorsXhrAllowedUris(allowedUris);
		corsFilter.setCorsXhrAllowedOrigins(allowedOrigins);
		corsFilter.initialize();
		return corsFilter;
	}

}
