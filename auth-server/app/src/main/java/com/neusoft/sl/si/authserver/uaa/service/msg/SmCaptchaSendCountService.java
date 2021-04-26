package com.neusoft.sl.si.authserver.uaa.service.msg;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class SmCaptchaSendCountService {

	private static final String CACHE_SM_CAPTCHA_SEND_COUNT_REDIS_KEY = "CACHE_SM_CAPTCHA_SEND_COUNT";

	@CachePut(value = CACHE_SM_CAPTCHA_SEND_COUNT_REDIS_KEY, key = "'CACHE_SM_CAPTCHA_SEND_COUNT_' + #mobile")
	public int put(String mobile, int count) {
		return count;
	}

	@Cacheable(value = CACHE_SM_CAPTCHA_SEND_COUNT_REDIS_KEY, key = "'CACHE_SM_CAPTCHA_SEND_COUNT_' + #mobile", unless = "null==#result")
	public int load(String mobile) {
		return 0;
	}

	@CacheEvict(value = CACHE_SM_CAPTCHA_SEND_COUNT_REDIS_KEY, key = "'CACHE_SM_CAPTCHA_SEND_COUNT_' + #mobile")
	public void remove(String mobile) {
	}

}
