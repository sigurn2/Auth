package com.neusoft.ehrss.liaoning.provider.ecard;

import java.net.SocketTimeoutException;
import java.util.Arrays;

import javax.annotation.Resource;

import org.apache.http.conn.HttpHostConnectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.neusoft.ehrss.liaoning.provider.ecard.request.EcardValidRequest;
import com.neusoft.ehrss.liaoning.provider.ecard.response.EcardCardResponse;
import com.neusoft.ehrss.liaoning.provider.ecard.response.EcardValidResponse;
import com.neusoft.ehrss.liaoning.security.implicit.alipay.controller.dto.BizContent;
import com.neusoft.sl.girder.utils.JsonMapper;
import com.neusoft.sl.si.authserver.uaa.exception.BusinessException;

@Service
public class EcardService {

	private static final Logger log = LoggerFactory.getLogger(EcardService.class);

	@Value("${saber.ecard.path}")
	private String path;

	private String valid = "/valid/busiSeq";
	private String card = "/card";

	@Resource(name = "customRestTemplate")
	private RestTemplate restTemplate;

	public EcardValidResponse valid(BizContent dto) {
		try {
			EcardValidRequest request = new EcardValidRequest();
			request.setChannelNo(dto.getChannelNo());
			request.setSignNo(dto.getSignNo());
			request.setBusiSeq(dto.getBusiSeq());

			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
			HttpEntity<EcardValidRequest> httpEntity = new HttpEntity<EcardValidRequest>(request, headers);
			ResponseEntity<String> entity = restTemplate.postForEntity(path + valid, httpEntity, String.class);
			log.debug("==调用电子社保卡身份验证接口http status={},http body={}", entity.getStatusCode().toString(), entity.getBody());
			check(entity.getBody());
			return JsonMapper.fromJson(entity.getBody(), EcardValidResponse.class);
		} catch (RestClientResponseException ex) {
			log.error("调用电子社保卡身份验证接口出错", ex);
			log.error("==调用电子社保卡身份验证接口出错 HTTP Status== {}", ex.getRawStatusCode());
			log.error("==请求执行结果== {}", ex.getResponseBodyAsString());
			check(ex.getResponseBodyAsString());
			throw new BusinessException("调用电子社保卡身份验证接口出错，请稍后再试");
		} catch (RestClientException ex) {
			log.error("调用电子社保卡身份验证接口出错", ex);
			if (ex.getCause() != null && (ex.getCause() instanceof HttpHostConnectException || ex.getCause() instanceof SocketTimeoutException)) {
				throw new BusinessException("与电子社保卡服务连接出现异常，请稍后再试");
			}
			throw new BusinessException("调用电子社保卡身份验证接口出错，请稍后再试");
		}
	}

	public EcardCardResponse card(String esscNo) {
		try {
			ResponseEntity<String> entity = restTemplate.getForEntity(path + card + "?esscNo=" + esscNo, String.class);
			log.debug("==调用社保卡信息查询接口http status={},http body={}", entity.getStatusCode().toString(), entity.getBody());
			check(entity.getBody());
			return JsonMapper.fromJson(entity.getBody(), EcardCardResponse.class);
		} catch (RestClientResponseException ex) {
			log.error("调用社保卡信息查询接口出错", ex);
			log.error("==调用社保卡信息查询接口出错 HTTP Status== {}", ex.getRawStatusCode());
			log.error("==请求执行结果== {}", ex.getResponseBodyAsString());
			check(ex.getResponseBodyAsString());
			throw new BusinessException("调用社保卡信息查询接口出错，请稍后再试");
		} catch (RestClientException ex) {
			log.error("调用社保卡信息查询接口出错", ex);
			if (ex.getCause() != null && (ex.getCause() instanceof HttpHostConnectException || ex.getCause() instanceof SocketTimeoutException)) {
				throw new BusinessException("与电子社保卡服务连接出现异常，请稍后再试");
			}
			throw new BusinessException("调用社保卡信息查询出错，请稍后再试");
		}
	}

	private void check(String response) {
		String errormsg = "调用电子社保卡身份验证失败";
		JsonNode jsonNode = JsonMapper.fromJsonNode(response);
		if (jsonNode.hasNonNull("detail")) {
			errormsg = jsonNode.get("detail").asText();
			throw new BusinessException(errormsg);
		} else if (jsonNode.hasNonNull("message")) {
			errormsg = jsonNode.get("message").asText();
			throw new BusinessException(errormsg);
		}
	}

}
