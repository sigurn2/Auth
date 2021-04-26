package com.neusoft.ehrss.liaoning.provider.cloudbae;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SignUtil {

	private static final Logger logger = LoggerFactory.getLogger(SignUtil.class);

	public static String generateYbbSignInfo(Map<String, String> data, String clientSecret) {
		String info = sortMapToStr(data);
		String target = String.format("%s&key=%s", new Object[] { info, clientSecret });
		try {
			Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
			SecretKeySpec secret_key = new SecretKeySpec(clientSecret.getBytes("UTF-8"), "HmacSHA256");
			sha256_HMAC.init(secret_key);
			byte[] array = sha256_HMAC.doFinal(target.getBytes("UTF-8"));
			StringBuilder sb = new StringBuilder();
			for (byte item : array) {
				sb.append(Integer.toHexString(item & 0xFF | 0x100).substring(1, 3));
			}
			return sb.toString().toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage(), e);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		} catch (InvalidKeyException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public static String sortMapToStr(Map<String, String> map) {
		if (null != map) {
			List<String> keys = new ArrayList<String>(map.keySet());
			Collections.sort(keys);
			StringBuilder authInfo = new StringBuilder();
			for (int i = 0; i < keys.size() - 1; i++) {
				String key = (String) keys.get(i);
				String value = (String) map.get(key);
				if (!key.equals("sign")) {
					if (((String) map.get(key)).trim().length() > 0) {
						authInfo.append(buildKeyValue(key, value, false));
						authInfo.append("&");
					}
				}
			}
			String tailKey = (String) keys.get(keys.size() - 1);
			String tailValue = (String) map.get(tailKey);
			authInfo.append(buildKeyValue(tailKey, tailValue, false));
			return authInfo.toString();
		}
		return "";
	}

	private static String buildKeyValue(String key, String value, boolean isEncode) {
		StringBuilder sb = new StringBuilder();
		sb.append(key);
		sb.append("=");
		if (isEncode) {
			try {
				sb.append(URLEncoder.encode(value, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				sb.append(value);
			}
		} else {
			sb.append(value);
		}
		return sb.toString();
	}

}
