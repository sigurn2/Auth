package com.neusoft.ehrss.liaoning.security.person;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.fasterxml.jackson.databind.JsonNode;
import com.neusoft.ehrss.liaoning.config.channel.ChannelConfiguration;
import com.neusoft.ehrss.liaoning.config.channel.dto.ChannelDTO;
import com.neusoft.ehrss.liaoning.provider.ecard.EcardService;
import com.neusoft.ehrss.liaoning.provider.ecard.response.EcardCardResponse;
import com.neusoft.ehrss.liaoning.security.person.ecard.utils.AESUtils;
import com.neusoft.ehrss.liaoning.security.person.ecard.utils.SignUtil;
import com.neusoft.sl.girder.utils.JsonMapper;

/**
 * 电子社保卡二维码登录Filter
 *
 */
public class QRCodeRequestProcessingFilter extends AbstractAuthenticationProcessingFilter {

	private static final Logger log = LoggerFactory.getLogger(QRCodeRequestProcessingFilter.class);

	private EcardService ecardService;

	public QRCodeRequestProcessingFilter(String filterProcessesUrl) {
		super(filterProcessesUrl);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
		String result = request.getParameter("result");
		Validate.notBlank(result);
		if (!result.equals("0")) {
			log.error("扫码登录失败，接到的result：{}", result);
			throw new BadCredentialsException("扫码登录失败，状态码：" + result);
		}
		String accessKey = request.getParameter("ak");
		String _api_timestamp = request.getParameter("_api_timestamp");
		String security = request.getParameter("security");
		String _api_signature = request.getParameter("_api_signature");
		if (org.apache.commons.lang3.StringUtils.isAnyBlank(accessKey, _api_timestamp, security, _api_signature)) {
			log.error("入参缺失，accessKey：{}，_api_timestamp：{}，security：{}，_api_signature：{}", accessKey, _api_timestamp, security, _api_signature);
			throw new BadCredentialsException("请求非法");
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

		try {
			Date date = DateUtils.addMinutes(sdf.parse(_api_timestamp), 10);
			if (date.before(new Date())) {
				throw new BadCredentialsException("请求已超时，请重新扫码");
			}
		} catch (ParseException e) {
			log.error("校验请求超时出错", e);
			throw new BadCredentialsException("校验请求超时出错");
		}

		ChannelDTO channelDTO = ChannelConfiguration.getChannelBLOServices().get(accessKey).getChannelDTO();

		// 验证签名
		Map<String, String> signMap = new HashMap<>();
		signMap.put("result", result);
		signMap.put("_api_timestamp", _api_timestamp);
		signMap.put("security", security);
		String sign = SignUtil.sign(signMap, channelDTO.getSecretKey());
		if (!sign.equals(_api_signature)) {
			log.error("签名验证失败，本地计算出签名值为：{}", sign);
			throw new BadCredentialsException("签名验证失败");
		}

		// 解密
		String bodyJson = AESUtils.decrypt(security, channelDTO.getEncryptKey());
		if (bodyJson == null) {
			throw new BadCredentialsException("解密失败");
		}
		// {"esscNo":"2AB3AC860247DDF1A9733564E2F5A7CC"}
		log.debug("解密获取到数据bodyJson：{}", bodyJson);
		JsonNode jsonNode = JsonMapper.fromJsonNode(bodyJson);
		// String idNumber = JsonMapper.getJsonNodeValue("aac002", jsonNode);
		// String name = JsonMapper.getJsonNodeValue("aac003", jsonNode);
		String esscNo = JsonMapper.getJsonNodeValue("esscNo", jsonNode);
		EcardCardResponse ecardCardResponse = ecardService.card(esscNo);

		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(ecardCardResponse.getSiNo(), ecardCardResponse.getName());
		setDetails(request, authRequest);
		return this.getAuthenticationManager().authenticate(authRequest);
	}

	protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	}

	public void setEcardService(EcardService ecardService) {
		this.ecardService = ecardService;
	}

}
