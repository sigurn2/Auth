package com.neusoft.ehrss.liaoning.security.implicit.wechat;

/**
 * 自定义成功回调
 * 
 * @author y_zhang.neu
 *
 */

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.neusoft.ehrss.liaoning.config.wechat.WechatConfig;
import com.neusoft.ehrss.liaoning.security.implicit.wechat.userdetails.WechatUserDetails;
import com.neusoft.ehrss.liaoning.utils.UrlPathUtils;
import com.neusoft.sl.girder.utils.JsonMapper;
import com.neusoft.sl.si.authserver.base.domains.log.UserLogonLog;
import com.neusoft.sl.si.authserver.base.services.redis.WxUserRedisService;
import com.neusoft.sl.si.authserver.uaa.log.UserLogManager;
import com.neusoft.sl.si.authserver.uaa.userdetails.SaberUserDetails;

public class WechatAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private static final Logger log = LoggerFactory.getLogger(WechatAuthenticationSuccessHandler.class);

	private DefaultTokenServices defaultAuthorizationServerTokenServices;

	private AntPathRequestMatcher requestMatcher;

	private WxUserRedisService wxUserRedisService;

	public WechatAuthenticationSuccessHandler() {
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
		// 处理登录日志
		Object principal = authentication.getPrincipal();
		if (principal != null) {
			SaberUserDetails details = (SaberUserDetails) principal;
			UserLogonLog log = UserLogManager.userLogonLogSync(details, request);
			details.setLogonlogid(log.getId());// 设置日志id
		}
		super.onAuthenticationSuccess(request, response, authentication);
	}

	public void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
		// 生成token
		Map<String, String> requestParameters = new HashMap<String, String>();
		requestParameters.put("client_id", "wechat");
		requestParameters.put("response_type", "token");
		Set<String> scopes = new HashSet<String>();
		scopes.add("read");
		Set<String> responseTypes = new HashSet<String>();
		responseTypes.add("token");

		OAuth2Request storedOAuth2Request = new OAuth2Request(requestParameters, "wechat", AuthorityUtils.createAuthorityList("ROLE_WECHAT"), true, scopes, null, null, responseTypes, null);
		OAuth2Authentication combinedAuth = new OAuth2Authentication(storedOAuth2Request, authentication);

		OAuth2AccessToken accessToken = defaultAuthorizationServerTokenServices.createAccessToken(combinedAuth);

		Map<String, String> map = extractUriTemplateVariables(request);
		String type = map.get("type");
		String pageId = map.get("pageId");

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("access_token", accessToken.getValue());
		data.put("token_type", accessToken.getTokenType());

		Object principal = authentication.getPrincipal();
		if (principal != null) {
			if (principal instanceof WechatUserDetails) {
				WechatUserDetails details = (WechatUserDetails) principal;
				WxUserDTO dto = wxUserRedisService.load(details.getOpenid());
				WxUserInfoUtils.assembleParamsMap(data, dto);
			}
		}

		String pageTemplet = WechatConfig.getPageTemplet(type);

		String new_Redirect = String.format(pageTemplet, pageId, getOutputParam(data));

		if (!UrlUtils.isAbsoluteUrl(new_Redirect)) {
			new_Redirect = UrlPathUtils.genDomainName(request) + new_Redirect;
		}

		log.debug("重定向地址new_Redirect={}", new_Redirect);

		getRedirectStrategy().sendRedirect(request, response, new_Redirect);
	}

	public String getOutputParam(Map<String, Object> token) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", "10000");
		result.put("message", "login success");
		result.put("data", token);
		return JsonMapper.toJson(result);
	}

	private Map<String, String> extractUriTemplateVariables(HttpServletRequest request) {
		return this.requestMatcher.extractUriTemplateVariables(request);
	}

	public void setDefaultAuthorizationServerTokenServices(DefaultTokenServices defaultAuthorizationServerTokenServices) {
		this.defaultAuthorizationServerTokenServices = defaultAuthorizationServerTokenServices;
	}

	public void setRedirectProcessesUrl(String redirectProcessesUrl) {
		this.requestMatcher = new AntPathRequestMatcher(redirectProcessesUrl);
	}

	public WxUserRedisService getWxUserRedisService() {
		return wxUserRedisService;
	}

	public void setWxUserRedisService(WxUserRedisService wxUserRedisService) {
		this.wxUserRedisService = wxUserRedisService;
	}

}
