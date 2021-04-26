package com.neusoft.ehrss.liaoning.config.wechat;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.neusoft.sl.si.authserver.uaa.exception.WechatException;

@Component
@ConfigurationProperties(prefix = "saber.wechat.server")
public class WechatConfig {

	private static Map<String, String> pageTemplet = new HashMap<String, String>();

	public Map<String, String> getPageTemplet() {
		return pageTemplet;
	}

	public void setPageTemplet(Map<String, String> pageTemplet) {
		WechatConfig.pageTemplet = pageTemplet;
	}

	public static String getPageTemplet(String type) {
		if (type == null || "".equals(type.trim())) {
			return null;
		}
		if (pageTemplet.containsKey(type))
			return pageTemplet.get(type);
		else
			throw new WechatException("请求的type=" + type + "没有配置pageTemplet");
	}

}
