package com.neusoft.ehrss.liaoning.provider.cloudbae;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.neusoft.ehrss.liaoning.provider.cloudbae.response.OauthTokenResponse;
import com.neusoft.ehrss.liaoning.provider.cloudbae.response.UserInfoResponse;
import com.neusoft.sl.girder.utils.JsonMapper;
import com.neusoft.sl.si.authserver.uaa.exception.BusinessException;

@Service
public class CloudbaeService {

	private static final Logger log = LoggerFactory.getLogger(CloudbaeService.class);

	@Value("${saber.cloudbae.client_id}")
	private String clientId;

	@Value("${saber.cloudbae.client_secret}")
	private String clientSecret;

	@Value("${saber.cloudbae.grant_type}")
	private String grantType;

	@Value("${saber.cloudbae.path}")
	private String path;

	@Value("${saber.cloudbae.redirect_uri}")
	private String redirectUri;

	private String getToken = "/oauth/token";

	private String queryPersonInfo = "/uc/openApi/user/v2/query";

	@Resource(name = "customRestTemplate")
	private RestTemplate restTemplate;

	public OauthTokenResponse getToken(String code) {
		try {
			MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
			params.add("client_id", clientId);
			params.add("client_secret", clientSecret);
			params.add("code", code);
			params.add("grant_type", grantType);
			params.add("redirect_uri", redirectUri);
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);
			ResponseEntity<String> entity = restTemplate.postForEntity(path + getToken, httpEntity, String.class);
			log.debug("==调用获取access_token接口http status={},http body={}", entity.getStatusCode().toString(), entity.getBody());
			check(entity.getBody());
			return JsonMapper.fromJson(genData("data", entity.getBody()), OauthTokenResponse.class);
		} catch (RestClientException ex) {
			log.error("调用获取access_token接口出错", ex);
			if (ex instanceof RestClientResponseException) {
				RestClientResponseException e = (RestClientResponseException) ex;
				log.error("==调用获取access_token接口出错 HTTP Status== {}", e.getRawStatusCode());
				log.error("==请求执行结果== {}", e.getResponseBodyAsString());
				check(e.getResponseBodyAsString());
				throw new BusinessException("调用获取access_token接口出错，请稍后再试");
			}
			throw new BusinessException(ex.getMessage());
		}
	}

	public UserInfoResponse queryPersonInfo(String accessToken) {
		try {
			Map<String, String> signMap = new HashMap<String, String>();
			signMap.put("client_id", clientId);
			String now = String.valueOf(new Date().getTime());
			signMap.put("time", now);
			signMap.put("queryParam", "access_token=" + accessToken);
			String sign = SignUtil.generateYbbSignInfo(signMap, clientSecret);
			String authtication = String.format("time=%s,client_id=%s,sign=%s", new Object[] { now, clientId, sign });
			log.debug("authtication:{}", authtication);

			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			headers.set("authtication", authtication);

			HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
			ResponseEntity<String> entity = restTemplate.exchange(path + queryPersonInfo + "?access_token={0}", HttpMethod.GET, httpEntity, String.class, accessToken);
			log.debug("==调用查询用户基本信息接口http status={},http body={}", entity.getStatusCode().toString(), entity.getBody());
			check(entity.getBody());
			return JsonMapper.fromJson(genData("data", entity.getBody()), UserInfoResponse.class);
		} catch (RestClientException ex) {
			log.error("调用查询用户基本信息接口出错", ex);
			if (ex instanceof RestClientResponseException) {
				RestClientResponseException e = (RestClientResponseException) ex;
				log.error("==调用查询用户基本信息接口出错 HTTP Status== {}", e.getRawStatusCode());
				log.error("==请求执行结果== {}", e.getResponseBodyAsString());
				check(e.getResponseBodyAsString());
				throw new BusinessException("调用查询用户基本信息接口出错，请稍后再试");
			}
			throw new BusinessException(ex.getMessage());
		}
	}

	private void check(String response) {
		String errormsg = "调用爱南宁返回错误";
		JsonNode jsonNode = JsonMapper.fromJsonNode(response);
		if (!jsonNode.hasNonNull("code") || !"10000".equals(jsonNode.get("code").asText())) {
			if (jsonNode.hasNonNull("message")) {
				errormsg = jsonNode.get("message").asText();
			}
			throw new BadCredentialsException(errormsg);
		}
	}

	private String genData(String key, String body) {
		JsonNode jsonNode = JsonMapper.fromJsonNode(body);
		JsonNode dataNode = jsonNode.get(key);
		JsonNodeType type = dataNode.getNodeType();
		String rs = "";
		switch (type) {
		case OBJECT:
			rs = dataNode.toString();
			break;
		case STRING:
			rs = dataNode.asText();
			break;
		case NULL:
			throw new BadCredentialsException("用户不存在");
		default:
			break;
		}
		return rs;
	}

}
