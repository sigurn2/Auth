package com.neusoft.sl.si.authserver.uaa.config.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.neusoft.sl.si.authserver.uaa.log.UserLogManager;
import com.neusoft.sl.si.authserver.uaa.userdetails.SaberUserDetails;

/**
 * RestFul请求登出以后清除token,不做转向处理
 * 
 * @author wuyf
 *
 */
@Component
public class RestfulLogoutSuccessHandler implements LogoutSuccessHandler {

	/** 日志 */
	private static Logger LOGGER = LoggerFactory.getLogger(RestfulLogoutSuccessHandler.class);

	@Autowired
	private ConsumerTokenServices tokenServices;

	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		LOGGER.debug("=========================================");
		LOGGER.debug("==========AuthServer Logout!=============");

		// OAuth2AccessToken token = tokenServices
		// .getAccessToken((OAuth2Authentication) authentication);

		if (authentication instanceof OAuth2Authentication) {
			OAuth2Authentication auth = (OAuth2Authentication) authentication;
			OAuth2AuthenticationDetails detail = (OAuth2AuthenticationDetails) auth.getDetails();
			LOGGER.debug("==remove token " + detail.getTokenValue() + "=============");
			tokenServices.revokeToken(detail.getTokenValue());
		} else {
			// UsernamePasswordAuthenticationToken auth =
			// (UsernamePasswordAuthenticationToken) authentication;
			// Object details = auth.getDetails();

			LOGGER.debug("Not OAuth2Authentication token");
			UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;
			// Object details = auth.getDetails();
			if (auth != null) {
				Object principal = auth.getPrincipal();
				// 登出日志
				if (principal instanceof SaberUserDetails) {
					SaberUserDetails details = (SaberUserDetails) principal;
					if(!StringUtils.isEmpty(details.getLogonlogid())){
						UserLogManager.updateLogoffTime(details.getLogonlogid(), "正常退出");
					}
				}
			}
		}

		LOGGER.debug("=========================================");
	}

}
