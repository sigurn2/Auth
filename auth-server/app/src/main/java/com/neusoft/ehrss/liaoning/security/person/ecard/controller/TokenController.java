package com.neusoft.ehrss.liaoning.security.person.ecard.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neusoft.ehrss.liaoning.config.channel.ChannelConfiguration;
import com.neusoft.ehrss.liaoning.config.channel.dto.ChannelDTO;
import com.neusoft.ehrss.liaoning.security.person.ecard.utils.AESUtils;
import com.neusoft.ehrss.liaoning.security.person.ecard.utils.SignUtil;
import com.neusoft.sl.girder.utils.JsonMapper;
import com.neusoft.sl.si.authserver.uaa.exception.BusinessException;

/**
 * Created by zhou.haidong on 2019/2/19.
 */
@RestController
@RequestMapping(value = "/ws/token")
public class TokenController {

	private final Logger logger = LoggerFactory.getLogger(TokenController.class);

	private static final String CHARSET_UTF_8 = StandardCharsets.UTF_8.toString();

	@Value("${saber.ecard.qrcodeUrl}")
	private String qrcodeUrl = "";

	@Value("${saber.ecard.returnUrl}")
	private String returnUrl = "";

	@PostMapping(value = "/sign", produces = { "application/json;charset=UTF-8;" })
	public Map<String, String> signByUserInfo(@RequestBody Map<String, String> params) throws Exception {
		return sign(params);
	}

	@GetMapping(value = "/sign", produces = { "application/json;charset=UTF-8;" })
	public Map<String, String> sign(@RequestParam String accessKey) throws Exception {
		Map<String, String> body = new HashMap<>();
		body.put("accessKey", accessKey);
		return sign(body);
	}

	@GetMapping(value = "/qrcode")
	public void sign(@RequestParam String accessKey, HttpServletResponse response) throws Exception {
		Map<String, String> body = new HashMap<>();
		body.put("accessKey", accessKey);
		response.sendRedirect(qrcodeUrl + "?" + sign(body).get("urlParams"));
	}

	private Map<String, String> sign(Map<String, String> params) throws Exception {
		// 根据ak获取sk、加密密钥、渠道编号等信息
		ChannelDTO channelDTO = ChannelConfiguration.getChannelBLOServices().get(params.get("accessKey")).getChannelDTO();

		if (channelDTO == null) {
			logger.debug("sign accessKey:{}", params.get("accessKey"));
			throw new BusinessException("channel is null.");
		}

		// 组装签名需要的参数
		Map<String, String> result = new HashMap<>();
		result.put("_api_name", "get_token");
		result.put("_api_version", "1.0.0");
		result.put("_api_access_key", channelDTO.getAccessKey());
		result.put("api_access_key", channelDTO.getAccessKey());
		result.put("_api_timestamp", System.currentTimeMillis() + "");

		params.put("channelNo", channelDTO.getChannelNo());
		String bodyJson = JsonMapper.toJson(params);
		String securityKey = "security";
		String returnUrlKey = "returnUrl";

		// 对请求参数进行AES加密
		result.put(securityKey, AESUtils.encrypt(bodyJson, channelDTO.getEncryptKey()));
		result.put(returnUrlKey, returnUrl + "?ak=" + channelDTO.getAccessKey());

		result.put("_api_signature", URLEncoder.encode(SignUtil.sign(result, channelDTO.getSecretKey()), CHARSET_UTF_8));
		// 签名之后对security和returnUrl进行URLEncoder，注意，一定要在签名后再URLEncoder
		result.put(securityKey, URLEncoder.encode(result.get(securityKey), CHARSET_UTF_8));
		result.put(returnUrlKey, URLEncoder.encode(result.get(returnUrlKey), CHARSET_UTF_8));

		// 将各签名参数拼接成一个字符串返回
		StringBuffer sb = new StringBuffer();
		result.entrySet().stream().forEach(es -> sb.append("&").append(es.getKey()).append("=").append(es.getValue()));
		String urlParams = sb.substring(1).toString();
		Map<String, String> map = new HashMap<>();
		map.put("urlParams", urlParams);
		return map;
	}

}
