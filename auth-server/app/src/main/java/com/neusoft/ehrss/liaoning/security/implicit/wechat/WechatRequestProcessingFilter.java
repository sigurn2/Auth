package com.neusoft.ehrss.liaoning.security.implicit.wechat;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import com.neusoft.ehrss.liaoning.provider.wx.WechatService;
import com.neusoft.ehrss.liaoning.provider.wx.response.WxUserResponse;
import com.neusoft.sl.si.authserver.base.services.redis.WxUserRedisService;

public class WechatRequestProcessingFilter extends UsernamePasswordAuthenticationFilter {

	private static final Logger log = LoggerFactory.getLogger(WechatRequestProcessingFilter.class);

	private AntPathRequestMatcher requestMatcher;

	private WechatService wechatService;

	private WxUserRedisService wxUserRedisService;

	public WechatRequestProcessingFilter(String filterProcessesUrl) {
		setFilterProcessesUrl(filterProcessesUrl);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		String code = request.getParameter("code");
		if (StringUtils.isEmpty(code))
			throw new BadCredentialsException("code is null");

		Map<String, String> map = this.requestMatcher.extractUriTemplateVariables(request);
		String type = map.get("type");
		String pageId = map.get("pageId");

		log.debug("获取微信的OPENID appId={}，pageId={}, code={}", type, pageId, code);

		WxUserResponse wxUserResponse = wechatService.getWxUser(type, code);
		
		WxUserDTO dto = new WxUserDTO();
		dto.setNickname(wxUserResponse.getNickname());
		dto.setSex(wxUserResponse.getSex());
		dto.setHeadimgurl(wxUserResponse.getHeadImgUrl());
		dto.setAppId(type);
		dto.setOpenid(wxUserResponse.getOpenId());

		wxUserRedisService.put(dto);

		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(dto.getOpenid(), "");
		setDetails(request, authRequest);
		return this.getAuthenticationManager().authenticate(authRequest);
	}

	public void setRedirectProcessesUrl(String redirectProcessesUrl) {
		this.requestMatcher = new AntPathRequestMatcher(redirectProcessesUrl);
	}

	public WechatService getWechatService() {
		return wechatService;
	}

	public void setWechatService(WechatService wechatService) {
		this.wechatService = wechatService;
	}

	public WxUserRedisService getWxUserRedisService() {
		return wxUserRedisService;
	}

	public void setWxUserRedisService(WxUserRedisService wxUserRedisService) {
		this.wxUserRedisService = wxUserRedisService;
	}

}
