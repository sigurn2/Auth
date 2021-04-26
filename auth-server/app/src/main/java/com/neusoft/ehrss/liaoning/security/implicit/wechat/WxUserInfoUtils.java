package com.neusoft.ehrss.liaoning.security.implicit.wechat;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class WxUserInfoUtils {

	public static void assembleParamsMap(Map<String, Object> data, WxUserDTO dto) {
		try {
			data.put("nickname", URLEncoder.encode(URLEncoder.encode(dto.getNickname(), "utf-8"), "utf-8"));
		} catch (UnsupportedEncodingException e) {
			data.put("nickname", "");
		}
		data.put("sex", dto.getSex());// 值为1时是男性，值为2时是女性，值为0时是未知
		data.put("headimgurl", dto.getHeadimgurl());
	}

}
