package com.neusoft.ehrss.liaoning.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.neusoft.sl.si.authserver.uaa.exception.WechatException;

public class OpenidUtil {

	private static final Logger log = LoggerFactory.getLogger(OpenidUtil.class);

	private static final long MAX_DIFF = 1000 * 60 * 60 * 8;// 8小时

	/**
	 * 解密
	 *
	 * @param
	 * @return
	 */
	public static OpenidObject dec(String value) {
		String dec_openid = "";
		try {
			dec_openid = AesUtil.aesDecryptHex(value);
		} catch (Exception e) {
			throw new WechatException("OPENID不合法");
		}
		String[] array_openid = dec_openid.split("\\|\\|");
		if (array_openid.length != 3) {
			throw new WechatException("OPENID不合法");
		}
		long l = Long.parseLong(array_openid[0]);
		long diff = System.currentTimeMillis() - l;
		if (diff > MAX_DIFF) {
			throw new WechatException("OPENID已失效");
		}

		String type = array_openid[1];
		String openid = array_openid[2];
		log.debug("解码出的type={},openid={}", type, openid);
		OpenidObject object = new OpenidObject(type, openid);
		return object;
	}

	/**
	 * 加密
	 *
	 * @param openid
	 * @return String
	 */
	public static String enc(String type, String openid) {
		String new_openid = System.currentTimeMillis() + "||" + type + "||" + openid;
		log.debug("生成新的new_openid={}", new_openid);
		try {
			new_openid = AesUtil.aesEncryptHex(new_openid);
		} catch (Exception e) {
			throw new WechatException("加密失败");
		}
		log.debug("加密后new_openid={}", new_openid);
		return new_openid;
	}

	public static void main(String[] args) {
		System.out.println(enc("zhrs", "12345678999"));
	}

}
