package com.neusoft.ehrss.liaoning.security.enterprise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.neusoft.ehrss.liaoning.security.AbstractSecurityConfiguration;
import com.neusoft.ehrss.liaoning.security.UserLoginAuthenticationSuccessHandler;
import com.neusoft.sl.si.authserver.uaa.userdetails.company.EnterpriseUserDetailService;

/**
 * 企业用户通过用户名密码登录安全配置
 * 
 */
// @Profile({ "dev", "dev_cloud", "test" })
@Configuration
@Order(-30)
public class EnterpriseSecurityConfiguration extends AbstractSecurityConfiguration {

	@Autowired
	private EnterpriseUserDetailService userDetailsService;

	@Value("${saber.auth.enterprise.url}")
	private String enterpriseUrl = "";

	@Value("${saber.auth.renshi.url}")
	private String renshiUrl = "";

	@Override
	protected UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	@Override
	protected String getFilterProcessesUrl() {
		return "/api/enterprise/usernamepassword/login";
	}

	@Override
	protected String getTargetUrl() {
		return enterpriseUrl;
	}

	@Override
	protected String getLoginFormUrl() {
		return "/enterpriselogin/";
	}

	@Override
	protected String[] getAntMatchers() {
		return new String[] { "/api/enterprise/usernamepassword/**" };
	}

	@Override
	protected SimpleUrlAuthenticationSuccessHandler getSuccessHandler() {
		UserLoginAuthenticationSuccessHandler successHandler = new UserLoginAuthenticationSuccessHandler(getTargetUrl());
		successHandler.setAlwaysUseDefaultTargetUrl(false);
		successHandler.setRenshiUrl(renshiUrl);
		return successHandler;
	}
}
