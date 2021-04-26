package com.neusoft.ehrss.liaoning.security.ca;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.util.StringUtils;

import com.neusoft.ehrss.liaoning.provider.ca.DFCA.DFCAGatewayService;
import com.neusoft.ehrss.liaoning.provider.ca.GXCA.GXCAGatewayService;

/**
 * 前后台分离处理CA Filter
 *
 */
public class CALoginAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

	private static final Logger log = LoggerFactory.getLogger(CALoginAuthenticationProcessingFilter.class);

	private DFCAGatewayService dfcaGatewayService;

	private GXCAGatewayService gxcaGatewayService;

	public CALoginAuthenticationProcessingFilter(String filterProcessesUrl, DFCAGatewayService dfcaGatewayService, GXCAGatewayService gxcaGatewayService) {
		super(filterProcessesUrl);
		this.dfcaGatewayService = dfcaGatewayService;
		this.gxcaGatewayService = gxcaGatewayService;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
		if (!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}
		String type = request.getParameter("type");// ca类型
		String companyNumber = request.getParameter("indata");// 单位编号
		String signdata = request.getParameter("signdata");// 签名值
		String certdata = request.getParameter("certdata");// 用户证书
		if (StringUtils.isEmpty(companyNumber) || StringUtils.isEmpty(signdata) || StringUtils.isEmpty(certdata) || StringUtils.isEmpty(type)) {
			throw new BadCredentialsException("获取CA数据为空，请重试");
		}
		companyNumber = companyNumber.replaceAll(" ", "+");
		signdata = signdata.replaceAll(" ", "+");
		certdata = certdata.replaceAll(" ", "+");
		log.info("CA数据【单位编号={}】,【随机数签名数据={}】,【签名证书={}】", companyNumber, signdata, certdata);

		// 获取随机数
		Object object = request.getSession().getAttribute("randomData");
		log.info("session中保存的随机数={}", object);
		if (object == null) {
			throw new BadCredentialsException("获取随机数失败，请重试");
		}

		String randrom = (String) object;

		if ("gx".equals(type)) {
			gxcaGatewayService.validateCert(certdata);
			gxcaGatewayService.verifySignedData(certdata, companyNumber + "|||" + randrom, signdata);
		} else if ("df".equals(type)) {
			dfcaGatewayService.checkCert(certdata);
			dfcaGatewayService.verify(certdata, companyNumber + "|||" + randrom, signdata);
		} else {
			throw new BadCredentialsException("请求的资源不存在");
		}
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(companyNumber, "");
		setDetails(request, authRequest);
		return this.getAuthenticationManager().authenticate(authRequest);
	}

	protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	}

}
