package com.neusoft.sl.si.authserver.uaa.config.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
	/**
	 * 注册新的视图
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/prevent/token").setViewName("/ws/token");
		// registry.addViewController("/personlogin").setViewName("personlogin");
		// registry.addViewController("/enterpriselogin").setViewName("enterpriselogin");
		// registry.addViewController("/siagentlogin").setViewName("siagentlogin");
		// registry.addViewController("/residentlogin").setViewName("residentlogin");

		// registry.addViewController("/oauth/confirm_access").setViewName("authorize");

		registry.addViewController("/api/person/idandmobile").setViewName("/oauth/authorize");
		// registry.addViewController("/api/enterprise/usernamepassword").setViewName("/oauth/authorize");
		registry.addViewController("/api/ca").setViewName("/oauth/authorize");
		// registry.addViewController("/api/siagent/usernamepassword").setViewName("/oauth/authorize");
		// registry.addViewController("/api/siagent/usernamepassword").setViewName("/oauth/authorize");
		// registry.addViewController("/api/resident/usernamepassword").setViewName("/oauth/authorize");
		// registry.addViewController("/api/sso/person").setViewName("/oauth/authorize");
		// 手机
		// registry.addViewController("/api/token/usernamepassword/login").setViewName("/oauth/token");
		// registry.addViewController("/api/token/usernamepassword/login").setViewName("/oauth/token");
		// 微信
		// registry.addViewController("/api/wechat/token/login").setViewName("/oauth/token");
		// registry.addViewController("/401.html").setViewName("401");
		// registry.addViewController("/404.html").setViewName("404");
		// registry.addViewController("/500.html").setViewName("500");
		// registry.addViewController("/calogin").setViewName("calogin");
	}
}
