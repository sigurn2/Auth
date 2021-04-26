package com.neusoft.ehrss.liaoning.security.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.neusoft.ehrss.liaoning.security.AbstractSecurityConfiguration;
import com.neusoft.sl.si.authserver.uaa.userdetails.person.PersonUserDetailService;

/**
 * 个人网厅登录SecurityConfiguration
 * 
 */
@Configuration
@Order(-33)
public class PersonSecurityConfiguration extends AbstractSecurityConfiguration {

	@Autowired
	private PersonUserDetailService userDetailsService;

	@Value("${saber.auth.person.url}")
	private String personUrl = "";

	@Override
	protected UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	@Override
	protected String getFilterProcessesUrl() {
		return "/api/person/idandmobile/login";
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
		return new String[] { "/api/person/idandmobile/**" };
	}
}
