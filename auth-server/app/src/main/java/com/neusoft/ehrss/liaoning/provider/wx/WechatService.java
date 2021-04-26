package com.neusoft.ehrss.liaoning.provider.wx;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.neusoft.ehrss.liaoning.provider.wx.response.WxUserResponse;
import com.neusoft.sl.girder.utils.JsonMapper;
import com.neusoft.sl.si.authserver.uaa.exception.WechatException;

@Service
public class WechatService {

	private static final Logger log = LoggerFactory.getLogger(WechatService.class);

	@Resource(name = "customRestTemplate")
	private RestTemplate restTemplate;

	@Value("${pile.wechat.address}")
	private String address;

	private static final String OAUTH2_WX_USER = "/oauth2/%s/user";

	public WxUserResponse getWxUser(String appid, String code) {
		try {
			ResponseEntity<WxUserResponse> responseEntity = restTemplate.getForEntity(address + String.format(OAUTH2_WX_USER, appid) + "?code=" + code, WxUserResponse.class);
			log.debug("==调用微信服务拉取人员基本信息 HTTP Status== {}", responseEntity.getStatusCodeValue());
			WxUserResponse response = responseEntity.getBody();
			return response;
		} catch (RestClientResponseException e) {
			log.error("==调用微信服务拉取人员基本信息 HTTP Status== {}", e.getRawStatusCode());
			log.error("==请求执行结果== {}", e.getResponseBodyAsString());
			throw new WechatException(response(e.getResponseBodyAsString()));
		} catch (RestClientException e) {
			log.error("调用微信服务拉取人员基本信息出错", e);
			throw new WechatException("调用微信服务拉取人员基本信息出错");
		}
	}

	private String response(String response) {
		JsonNode jsonNode = JsonMapper.fromJsonNode(response);
		if (jsonNode.hasNonNull("detail")) {
			return jsonNode.get("detail").asText();
		} else if (jsonNode.hasNonNull("message")) {
			return jsonNode.get("message").asText();
		} else if (jsonNode.hasNonNull("error")) {
			return jsonNode.get("error").asText();
		}
		return response;
	}

}
