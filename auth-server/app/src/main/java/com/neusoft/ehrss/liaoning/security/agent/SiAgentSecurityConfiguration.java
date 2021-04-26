package com.neusoft.ehrss.liaoning.security.agent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.neusoft.ehrss.liaoning.security.AbstractSecurityConfiguration;
import com.neusoft.sl.si.authserver.uaa.userdetails.siagent.SiAgentUserDetailService;

/**
 * 学校用户通过用户名密码登录安全配置
 * 
 */
@Configuration
@Order(-34)
public class SiAgentSecurityConfiguration extends AbstractSecurityConfiguration {

	@Autowired
	private SiAgentUserDetailService userDetailsService;

	@Value("${saber.auth.agent.url}")
	private String agentUrl = "";

	@Override
	protected UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	@Override
	protected String getFilterProcessesUrl() {
		return "/api/siagent/usernamepassword/login";
	}

	@Override
	protected String getTargetUrl() {
		return agentUrl;
	}

	@Override
	protected String getLoginFormUrl() {
		return "/siagentlogin/";
	}

	@Override
	protected String[] getAntMatchers() {
		return new String[] { "/api/siagent/usernamepassword/**" };
	}
}
