package com.neusoft.ehrss.liaoning.security.expert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.neusoft.ehrss.liaoning.security.AbstractSecurityConfiguration;

/**
 * 专家网厅登录SecurityConfiguration
 * 
 */
@Configuration
@Order(-36)
public class ExpertSecurityConfiguration extends AbstractSecurityConfiguration {

	@Autowired
	private ExpertUserDetailsService userDetailsService;

	@Value("${saber.auth.expert.url}")
	private String expertUrl = "";

	@Override
	protected UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	@Override
	protected String getFilterProcessesUrl() {
		return "/api/expert/login";
	}

	@Override
	protected String getTargetUrl() {
		return expertUrl;
	}

	@Override
	protected String getLoginFormUrl() {
		return "/expertlogin/";
	}

	@Override
	protected String[] getAntMatchers() {
		return new String[] { "/api/expert/**" };
	}
}
