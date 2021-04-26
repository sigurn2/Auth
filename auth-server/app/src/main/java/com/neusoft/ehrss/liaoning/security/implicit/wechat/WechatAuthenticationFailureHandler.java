package com.neusoft.ehrss.liaoning.security.implicit.wechat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.neusoft.ehrss.liaoning.config.wechat.WechatConfig;
import com.neusoft.ehrss.liaoning.utils.OpenidUtil;
import com.neusoft.ehrss.liaoning.utils.UrlPathUtils;
import com.neusoft.sl.girder.utils.JsonMapper;
import com.neusoft.sl.si.authserver.base.services.redis.WxUserRedisService;
import com.neusoft.sl.si.authserver.uaa.exception.WechatNotBindException;

public class WechatAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	private static final Logger log = LoggerFactory.getLogger(WechatAuthenticationFailureHandler.class);

	private AntPathRequestMatcher requestMatcher;
	
	private WxUserRedisService wxUserRedisService;

	public WechatAuthenticationFailureHandler() {
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		String code = "90000";
		String message = "";
		Map<String, Object> data = null;
		
		if (exception.getCause() != null && exception.getCause() instanceof WechatNotBindException) {
			data = new HashMap<String, Object>();
			code = "90001";
			
			WechatNotBindException ex = (WechatNotBindException) exception.getCause();
			String openid = ex.getOpenid();
			
			WxUserDTO dto = wxUserRedisService.load(openid);
			
			data.put("openid", OpenidUtil.enc(dto.getAppId(), openid));
			WxUserInfoUtils.assembleParamsMap(data, dto);
			message = ex.getMessage();
		} else {
			message = exception.getMessage();
		}

		Map<String, String> map = extractUriTemplateVariables(request);
		String type = map.get("type");
		String pageId = map.get("pageId");

		String pageTemplet = WechatConfig.getPageTemplet(type);

		String new_Redirect = String.format(pageTemplet, pageId, getOutputParam(code, message, data));

		if (!UrlUtils.isAbsoluteUrl(new_Redirect)) {
			new_Redirect = UrlPathUtils.genDomainName(request) + new_Redirect;
		}

		log.debug("重定向地址new_Redirect={}", new_Redirect);
		getRedirectStrategy().sendRedirect(request, response, new_Redirect);
	}

	public String getOutputParam(String code, String message, Map<String, Object> map) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", code);
		try {
			result.put("message", URLEncoder.encode(URLEncoder.encode(message, "utf-8"), "utf-8"));
		} catch (UnsupportedEncodingException e) {
			result.put("message", "failure");
		}
		result.put("data", map);
		return JsonMapper.toJson(result);
	}

	private Map<String, String> extractUriTemplateVariables(HttpServletRequest request) {
		return this.requestMatcher.extractUriTemplateVariables(request);
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
