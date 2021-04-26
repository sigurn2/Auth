package com.neusoft.ehrss.liaoning.provider.ca.DFCA;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.neusoft.sl.si.authserver.uaa.exception.BusinessException;

@Service
public class DFCAGatewayService {

	private final static Map<String, String> returnCodeMap = new HashMap<String, String>();

	static {
		returnCodeMap.put("0", "正确返回");
		returnCodeMap.put("1", "取消操作");
		returnCodeMap.put("5", "通用错误");
		returnCodeMap.put("48", "设备错误（签名或加密数据不对）");
		returnCodeMap.put("769", "执行失败，未知错误");
		returnCodeMap.put("770", "执行出现异常");
		returnCodeMap.put("771", "初始化设备错误");
		returnCodeMap.put("773", "登录失败");
		returnCodeMap.put("774", "打开会话失败");
		returnCodeMap.put("775", "获取私钥失败");
		returnCodeMap.put("776", "获取公钥失败");
		returnCodeMap.put("777", "获取加密密钥失败");
		returnCodeMap.put("778", "待处理的数据太大");
		returnCodeMap.put("788", "获取私钥参数失败");
		returnCodeMap.put("789", "获取公钥参数失败");
		returnCodeMap.put("790", "输入参数错误");
		returnCodeMap.put("791", "输入数据长度太短");
		returnCodeMap.put("802", "条目为空（查询的内容为空）");
		returnCodeMap.put("1025", "操作异常");
		returnCodeMap.put("1026", "设备初始化异常");
		returnCodeMap.put("1027", "加载加密机/卡动态库失败");
		returnCodeMap.put("1028", "释放加密机/卡动态库失败");
		returnCodeMap.put("1029", "获取加密函数列表错误");
		returnCodeMap.put("1030", "证书格式错误");
		returnCodeMap.put("1031", "证书已过期");
		returnCodeMap.put("1032", "证书已吊销");
		returnCodeMap.put("1033", "证书颁发机构不可信");
		returnCodeMap.put("1040", "CRL文件不存在");
		returnCodeMap.put("1041", "CRL已过期");
		returnCodeMap.put("-1", "通用错误");

		returnCodeMap.put("0x00000000", "正确返回");
		returnCodeMap.put("0x00000001", "取消操作");
		returnCodeMap.put("0x00000005", "通用错误");
		returnCodeMap.put("0x00000030", "设备错误（签名或加密数据不对）");
		returnCodeMap.put("0x00000301", "执行失败，未知错误");
		returnCodeMap.put("0x00000302", "执行出现异常");
		returnCodeMap.put("0x00000303", "初始化设备错误");
		returnCodeMap.put("0x00000305", "登录失败");
		returnCodeMap.put("0x00000306", "打开会话失败");
		returnCodeMap.put("0x00000307", "获取私钥失败");
		returnCodeMap.put("0x00000308", "获取公钥失败");
		returnCodeMap.put("0x00000309", "获取加密密钥失败");
		returnCodeMap.put("0x00000310", "待处理的数据太大");
		returnCodeMap.put("0x00000314", "获取私钥参数失败");
		returnCodeMap.put("0x00000315", "获取公钥参数失败");
		returnCodeMap.put("0x00000316", "输入参数错误");
		returnCodeMap.put("0x00000317", "输入数据长度太短");
		returnCodeMap.put("0x00000322", "条目为空（查询的内容为空）");
		returnCodeMap.put("0x00000401", "操作异常");
		returnCodeMap.put("0x00000402", "设备初始化异常");
		returnCodeMap.put("0x00000403", "加载加密机/卡动态库失败");
		returnCodeMap.put("0x00000404", "释放加密机/卡动态库失败");
		returnCodeMap.put("0x00000405", "获取加密函数列表错误");
		returnCodeMap.put("0x00000406", "证书格式错误");
		returnCodeMap.put("0x00000407", "证书已过期");
		returnCodeMap.put("0x00000408", "证书已吊销");
		returnCodeMap.put("0x00000409", "证书颁发机构不可信");
		returnCodeMap.put("0x00000410", "CRL文件不存在");
		returnCodeMap.put("0x00000411", "CRL已过期");
	}

	private static final Logger log = LoggerFactory.getLogger(DFCAGatewayService.class);

	@Value("${saber.dfca.webservice.url}")
	private String webserviceUrl;

	@Resource(name = "customRestTemplate")
	private RestTemplate restTemplate;

	public boolean verify(String cert, String data, String signdata) {
		try {
			MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
			params.add("cert", cert);
			params.add("data", data);
			params.add("signdata", signdata);
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);
			ResponseEntity<String> entity = restTemplate.postForEntity(webserviceUrl + "{0}", httpEntity, String.class, "Verify");
			log.debug("==调用DFCA数据验签接口http status={}", entity.getStatusCode().toString());
			String body = entity.getBody();
			log.debug("==调用DFCA数据验签接口body={}", body);
			String code = substringBetween(body);
			if ("0".equals(code) || "0x00000000".equals(code)) {
				return true;
			}
			throw new BusinessException("数据验签失败：" + getMsg(code));
		} catch (RestClientException ex) {
			log.error("调用DFCA数据验签接口出错", ex);
			if (ex instanceof RestClientResponseException) {
				RestClientResponseException e = (RestClientResponseException) ex;
				log.error("==调用DFCA数据验签接口出错 HTTP Status== {}", e.getRawStatusCode());
				log.error("==请求执行结果== {}", e.getResponseBodyAsString());
				throw new BusinessException("调用DFCA数据验签接口出错，请稍后再试");
			}
			throw new BusinessException(ex.getMessage());
		}
	}

	public boolean checkCert(String cert) {
		try {
			MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
			params.add("cert", cert);
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);
			ResponseEntity<String> entity = restTemplate.postForEntity(webserviceUrl + "{0}", httpEntity, String.class, "CheckCert");
			log.debug("==调用DFCA证书验证接口http status={}", entity.getStatusCode().toString());
			String body = entity.getBody();
			log.debug("==调用DFCA证书验证接口body={}", body);
			String code = substringBetween(body);
			if ("0".equals(code) || "0x00000000".equals(code)) {
				return true;
			}
			throw new BusinessException("证书验证失败：" + getMsg(code));
		} catch (RestClientException ex) {
			log.error("调用DFCA证书验证接口出错", ex);
			if (ex instanceof RestClientResponseException) {
				RestClientResponseException e = (RestClientResponseException) ex;
				log.error("==调用DFCA证书验证接口出错 HTTP Status== {}", e.getRawStatusCode());
				log.error("==请求执行结果== {}", e.getResponseBodyAsString());
				throw new BusinessException("调用DFCA证书验证接口出错，请稍后再试");
			}
			throw new BusinessException(ex.getMessage());
		}
	}

	private String substringBetween(String xml) {
		return substringBetween(xml, "<string xmlns=\"http://tempuri.org/\">", "</string>");
	}

	private String substringBetween(String xml, String start, String end) {
		int beginIndex = xml.indexOf(start);
		int endIndex = xml.indexOf(end, beginIndex);
		return xml.substring(beginIndex, endIndex).replace(start, "");
	}

	private String getMsg(String returnCode) {
		if (returnCodeMap.containsKey(returnCode)) {
			return returnCodeMap.get(returnCode);
		}
		return returnCode;
	}

}
