package com.neusoft.ehrss.liaoning.provider.GongAn;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.neusoft.ehrss.liaoning.provider.GongAn.response.GetTimestampResponse;
import com.neusoft.ehrss.liaoning.provider.GongAn.response.IdentityAuthResponse;
import com.neusoft.ehrss.liaoning.provider.GongAn.response.content.GetTimestampContent;
import com.neusoft.ehrss.liaoning.provider.GongAn.response.content.IdentityAuthContent;
import com.neusoft.sl.si.authserver.uaa.exception.BusinessException;

@Service
public class GaAuthService {

	private final static Map<String, String> returnCodeMap = new HashMap<String, String>();

	static {
		returnCodeMap.put("200", "执行成功");
		returnCodeMap.put("451", "输入数据验签失败");
		returnCodeMap.put("452", "暂无记录");
		returnCodeMap.put("453", "服务密码错误");
		returnCodeMap.put("457", "请求数超过最大限度");
		returnCodeMap.put("458", "证书还未生成");
		returnCodeMap.put("471", "应用 id 错误");
		returnCodeMap.put("472", "用户帐号错误");
		returnCodeMap.put("473", "请求参数格式错误");
		returnCodeMap.put("474", "时间戳已过期");
		returnCodeMap.put("475", "输入的数据不能为空");
		returnCodeMap.put("500", "系统异常");
	}

	private static final Logger log = LoggerFactory.getLogger(GaAuthService.class);

	@Value("${saber.sauth.appId}")
	private String appId;

	@Value("${saber.sauth.appKey}")
	private String appKey;

	@Value("${saber.sauth.businessUserID}")
	private String businessUserID;

	@Value("${saber.sauth.url}")
	private String url;

	@Resource(name = "customRestTemplate")
	private RestTemplate restTemplate;

	@Value("${saber.sauth.httpTrue}")
	private boolean httpTrue = true;

	private String getMsg(String returnCode) {
		if (returnCodeMap.containsKey(returnCode)) {
			return returnCodeMap.get(returnCode);
		}
		return returnCode;
	}

	private String getSignData(MultiValueMap<String, String> params, String appkey) {
		StringBuffer content = new StringBuffer();
		params.remove("signature");
		// 按照key做排序
		String[] keys = params.keySet().toArray(new String[0]);
		Arrays.sort(keys);
		for (int i = 0; i < keys.length; i++) {
			String value = (String) params.getFirst(keys[i]);
			content.append(value);
		}
		content.append(appkey);
		return DigestUtils.sha1Hex(content.toString()).toUpperCase();
	}

	public IdentityAuthContent identityAuth(String idNumber, String name) {
		return identityAuth("1", idNumber, name, null);
	}

	public IdentityAuthContent identityAuth(String idNumber, String name, String pic) {
		return identityAuth("2", idNumber, name, pic);
	}

	public IdentityAuthContent identityAuth(String authLevel, String idNumber, String name, String pic) {
		if (httpTrue) {
			try {
				MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
				params.add("name", name);
				params.add("pno", idNumber);
				if (!StringUtils.isEmpty(pic)) {
					params.add("pic", pic);
				}
				params.add("authLevel", authLevel);
				params.add("appId", appId);
				params.add("businessUserID", businessUserID);
				params.add("timestamp", getTimestamp().getTimestamp());
				String signature = getSignData(params, appKey);
				params.add("signature", signature);
				HttpHeaders headers = new HttpHeaders();
				headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
				headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
				HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);
				ResponseEntity<IdentityAuthResponse> entity = restTemplate.postForEntity(url + "{0}", httpEntity, IdentityAuthResponse.class, "identityAuth");
				log.debug("==调用身份认证接口http status={}", entity.getStatusCode().toString());
				IdentityAuthResponse response = entity.getBody();
				log.debug("resultCode={},resultMessage={},errCode={}", response.getResultCode(), response.getResultMessage(), response.getErrCode());
				if ("Success".equals(response.getResultCode())) {
					log.debug("authCode={},authResult={}", response.getContent().getAuthCode(), response.getContent().getAuthResult());
					if (!"Y".equals(response.getContent().getAuthResult())) {
						throw new BusinessException("姓名和身份证号比对未通过，请输入真实的姓名和身份证号");
					}
					return response.getContent();
				} else {
					throw new BusinessException(getMsg(response.getResultMessage()));
				}
			} catch (RestClientResponseException e) {
				log.error("==调用身份认证接口出错 HTTP Status== {}", e.getRawStatusCode());
				String errmsg = e.getResponseBodyAsString();
				log.error("==请求执行结果== {}", errmsg);
				throw new BusinessException("调用身份认证接口出错，请稍后再试");
			} catch (Exception e) {
				log.error("调用身份认证接口出错", e);
				if (e instanceof BusinessException) {
					throw new BusinessException(e.getMessage());
				}
				throw new BusinessException("调用身份认证接口出错：" + e.getMessage());
			}
		} else {
			IdentityAuthContent test = new IdentityAuthContent();
			test.setAuthResult("Y");
			test.setAuthCode("200");
			test.setSignature("test");
			return test;
		}
	}

	private GetTimestampContent getTimestamp() {
		try {
			MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
			params.add("appId", appId);
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<MultiValueMap<String, Object>>(params, headers);
			ResponseEntity<GetTimestampResponse> entity = restTemplate.postForEntity(url + "{0}", httpEntity, GetTimestampResponse.class, "getTimestamp");
			log.debug("==调用获取服务器时间http status={}", entity.getStatusCode().toString());
			GetTimestampResponse response = entity.getBody();
			if ("Success".equals(response.getResultCode())) {
				String sig = DigestUtils.sha1Hex(response.getContent().getTimestamp() + appKey).toUpperCase();
				log.debug("本地签名值={}，返回的签名值={}", sig, response.getContent().getSignature());
				if (sig.equals(response.getContent().getSignature())) {
					return response.getContent();
				} else {
					throw new BusinessException("签名值不一致");
				}
			} else {
				throw new BusinessException(getMsg(response.getResultMessage()));
			}
		} catch (RestClientResponseException e) {
			log.error("==调用获取服务器时间接口出错 HTTP Status== {}", e.getRawStatusCode());
			log.error("==请求执行结果== {}", e.getResponseBodyAsString());
			throw new BusinessException("调用获取服务器时间接口出错，请稍后再试");
		} catch (Exception e) {
			log.error("调用获取服务器时间接口出错", e);
			if (e instanceof BusinessException) {
				throw new BusinessException(e.getMessage());
			}
			throw new BusinessException("调用获取服务器时间接口出错：" + e.getMessage());
		}
	}

}
