package com.neusoft.ehrss.liaoning.security.implicit.alipay;

/**
 * 自定义成功回调
 * 
 * @author y_zhang.neu
 *
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.neusoft.ehrss.liaoning.security.userdetails.redis.PersonRedisUser;
import com.neusoft.ehrss.liaoning.security.userdetails.redis.PersonRedisUserManager;
import com.neusoft.sl.girder.utils.JsonMapper;
import com.neusoft.sl.si.authserver.base.domains.log.UserLogonLog;
import com.neusoft.sl.si.authserver.uaa.controller.user.dto.AuthenticatedCasUserByRedis;
import com.neusoft.sl.si.authserver.uaa.controller.user.dto.UserAssembler;
import com.neusoft.sl.si.authserver.uaa.log.UserLogManager;
import com.neusoft.sl.si.authserver.uaa.userdetails.SaberUserDetails;

public class AlipayAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private static final Logger log = LoggerFactory.getLogger(AlipayAuthenticationSuccessHandler.class);

	private DefaultTokenServices defaultAuthorizationServerTokenServices;

	private PersonRedisUserManager personRedisUserManager;

	public AlipayAuthenticationSuccessHandler() {
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
		// 处理登录日志
		// Object principal = authentication.getPrincipal();
		// if (principal != null) {
		// SaberUserDetails details = (SaberUserDetails) principal;
		// UserLogonLog log = UserLogManager.userLogonLogSync(details, request);
		// details.setLogonlogid(log.getId());// 设置日志id
		// }
		super.onAuthenticationSuccess(request, response, authentication);
	}

	public void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
		String clientId = "";
		Object principal = authentication.getPrincipal();
		if (principal != null) {
			SaberUserDetails details = (SaberUserDetails) principal;
			UserLogonLog log = UserLogManager.userLogonLogSync(details, request);
			details.setLogonlogid(log.getId());// 设置日志id
			clientId = details.getClientType();
		}

		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

		// 生成token
		Map<String, String> requestParameters = new HashMap<String, String>();
		requestParameters.put("client_id", clientId);
		// requestParameters.put("redirect_uri", targetUrl);
		requestParameters.put("response_type", "token");
		Set<String> scopes = new HashSet<String>();
		scopes.add("read");
		Set<String> responseTypes = new HashSet<String>();
		responseTypes.add("token");

		OAuth2Request storedOAuth2Request = new OAuth2Request(requestParameters, clientId, authorities, true, scopes, null, null, responseTypes, null);
		OAuth2Authentication combinedAuth = new OAuth2Authentication(storedOAuth2Request, authentication);

		OAuth2AccessToken accessToken = defaultAuthorizationServerTokenServices.createAccessToken(combinedAuth);

		PersonRedisUser personRedisUser = personRedisUserManager.load(authentication.getName());
		AuthenticatedCasUserByRedis userByRedis = UserAssembler.toDTOByRedis(personRedisUser);
		userByRedis.setAccessToken(accessToken.getValue());
		userByRedis.setTokenType(accessToken.getTokenType());
		userByRedis.setExpiresIn(accessToken.getExpiresIn());
		userByRedis.setScope(accessToken.getScope());

		sendSuccess(response, userByRedis);
	}

	private void sendSuccess(HttpServletResponse response, AuthenticatedCasUserByRedis userByRedis) throws ServletException, IOException {
		log.debug("==========use sendSuccess response={}", userByRedis);
		String json = JsonMapper.toJson(userByRedis);
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setStatus(HttpStatus.OK.value());
		String contentType = "application/json";
		response.setContentType(contentType);
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}

	public void setDefaultAuthorizationServerTokenServices(DefaultTokenServices defaultAuthorizationServerTokenServices) {
		this.defaultAuthorizationServerTokenServices = defaultAuthorizationServerTokenServices;
	}

	public PersonRedisUserManager getPersonRedisUserManager() {
		return personRedisUserManager;
	}

	public void setPersonRedisUserManager(PersonRedisUserManager personRedisUserManager) {
		this.personRedisUserManager = personRedisUserManager;
	}

}
