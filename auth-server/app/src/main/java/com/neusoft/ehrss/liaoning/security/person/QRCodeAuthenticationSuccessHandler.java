package com.neusoft.ehrss.liaoning.security.person;

/**
 * 自定义成功回调
 * 
 * @author y_zhang.neu
 *
 */

import java.io.IOException;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.StringUtils;

import com.neusoft.ehrss.liaoning.utils.UrlPathUtils;
import com.neusoft.sl.si.authserver.base.domains.log.UserLogonLog;
import com.neusoft.sl.si.authserver.uaa.log.UserLogManager;
import com.neusoft.sl.si.authserver.uaa.service.code.SaberInCacheAuthorizationCodeServices;
import com.neusoft.sl.si.authserver.uaa.userdetails.SaberUserDetails;
import com.neusoft.sl.si.authserver.uaa.userdetails.company.EnterpriseCAUserDetails;
import com.neusoft.sl.si.authserver.uaa.userdetails.company.EnterpriseUserDetails;

public class QRCodeAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private static final Logger log = LoggerFactory.getLogger(QRCodeAuthenticationSuccessHandler.class);

	private RequestCache requestCache = new HttpSessionRequestCache();

	private String renshiUrl = "";

	public QRCodeAuthenticationSuccessHandler(String defaultTargetUrl) {
		super(defaultTargetUrl);
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

		SavedRequest savedRequest = requestCache.getRequest(request, response);

		if (savedRequest == null) {
			super.onAuthenticationSuccess(request, response, authentication);
			return;
		}

		String targetUrlParameter = getTargetUrlParameter();
		if (isAlwaysUseDefaultTargetUrl() || (targetUrlParameter != null && StringUtils.hasText(request.getParameter(targetUrlParameter)))) {
			requestCache.removeRequest(request, response);
			super.onAuthenticationSuccess(request, response, authentication);
			return;
		}

		clearAuthenticationAttributes(request);

		// Use the DefaultSavedRequest URL
		String targetUrl = savedRequest.getRedirectUrl();
		logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
		getRedirectStrategy().sendRedirect(request, response, targetUrl);
		// sendSuccess(response, targetUrl);
	}

	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		String targetUrl = super.determineTargetUrl(request, response);
		if (!UrlUtils.isAbsoluteUrl(targetUrl)) {
			targetUrl = UrlPathUtils.genDomainName(request) + targetUrl;
		}
		String clientId = "acme";
		// CA登录
		if (authentication.getPrincipal() instanceof EnterpriseCAUserDetails || authentication.getPrincipal() instanceof EnterpriseUserDetails) {
			// 判断登录角色
			Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_LABOUR_RS_ENTERPRISE_USER");
			if (authorities.size() == 1 && authorities.contains(grantedAuthority)) {
				log.debug("只有人事角色，跳转到人事系统");
				targetUrl = renshiUrl;
				clientId = "renshi";
			}
		}
		// 生成code
		Map<String, String> requestParameters = new HashMap<String, String>();
		requestParameters.put("client_id", clientId);
		requestParameters.put("redirect_uri", targetUrl);
		requestParameters.put("response_type", "code");
		Set<String> scopes = new HashSet<String>();
		scopes.add("read");
		scopes.add("write");
		Set<String> responseTypes = new HashSet<String>();
		responseTypes.add("code");
		OAuth2Request storedOAuth2Request = new OAuth2Request(requestParameters, clientId, AuthorityUtils.createAuthorityList("ROLE_PC"), true, scopes, null, targetUrl, responseTypes, null);
		OAuth2Authentication combinedAuth = new OAuth2Authentication(storedOAuth2Request, authentication);
		RandomValueAuthorizationCodeServices authorizationCodeServices = new SaberInCacheAuthorizationCodeServices();
		String code = authorizationCodeServices.createAuthorizationCode(combinedAuth);

		log.debug("==========use redirectUrl={}", targetUrl);

		if (-1 == targetUrl.indexOf("?")) {
			targetUrl += "?";
		} else {
			targetUrl += "&";
		}
		getRedirectStrategy().sendRedirect(request, response, targetUrl + "code=" + code);
		// sendSuccess(response, targetUrl + "code=" + code);
	}

	public void setRequestCache(RequestCache requestCache) {
		this.requestCache = requestCache;
	}

	/**
	 * 
	 * 
	 * { "redirectUri": "http://kq.neusoft.com?code=LZ5AiB", "status": 200 }
	 * 
	 * 
	 * @param response
	 * @param code
	 * @param redirectUri
	 * @throws ServletException
	 * @throws IOException
	 */
	// private void sendSuccess(HttpServletResponse response, String
	// redirectUri) throws ServletException, IOException {
	// log.debug("==========use sendSuccess redirectUri={}", redirectUri);
	// Map<String, Object> map = new HashMap<String, Object>();
	// map.put("status", HttpServletResponse.SC_OK);
	// map.put("redirectUri", redirectUri);
	// String json = JSON.toJSONString(map);
	// String contentType = "application/json";
	// response.setContentType(contentType);
	// response.setCharacterEncoding("UTF-8");
	// PrintWriter out = response.getWriter();
	// out.print(json);
	// out.flush();
	// out.close();
	// }

	public String getRenshiUrl() {
		return renshiUrl;
	}

	public void setRenshiUrl(String renshiUrl) {
		this.renshiUrl = renshiUrl;
	}
}
