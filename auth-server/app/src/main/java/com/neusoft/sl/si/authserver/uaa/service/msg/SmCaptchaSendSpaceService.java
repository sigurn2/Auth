package com.neusoft.sl.si.authserver.uaa.service.msg;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class SmCaptchaSendSpaceService {

	private static final String CACHE_SM_CAPTCHA_SEND_SPACE_REDIS_KEY = "CACHE_SM_CAPTCHA_SEND_SPACE";

	@CachePut(value = CACHE_SM_CAPTCHA_SEND_SPACE_REDIS_KEY, key = "'CACHE_SM_CAPTCHA_SEND_SPACE_' + #mobile")
	public String put(String mobile) {
		return mobile;
	}

	@Cacheable(value = CACHE_SM_CAPTCHA_SEND_SPACE_REDIS_KEY, key = "'CACHE_SM_CAPTCHA_SEND_SPACE_' + #mobile", unless = "null==#result")
	public String load(String mobile) {
		return null;
	}

	@CacheEvict(value = CACHE_SM_CAPTCHA_SEND_SPACE_REDIS_KEY, key = "'CACHE_SM_CAPTCHA_SEND_SPACE_' + #mobile")
	public void remove(String mobile) {
	}

}
