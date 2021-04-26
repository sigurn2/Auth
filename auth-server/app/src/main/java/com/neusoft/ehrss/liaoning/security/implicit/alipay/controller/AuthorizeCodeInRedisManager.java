package com.neusoft.ehrss.liaoning.security.implicit.alipay.controller;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.neusoft.ehrss.liaoning.security.implicit.alipay.controller.dto.AuthorizeCodeUser;

@Service
public class AuthorizeCodeInRedisManager {

	public static final String CACHE_AUTHORIZE_CODE_REDIS_KEY = "CACHE_AUTHORIZE_CODE";

	@CachePut(value = CACHE_AUTHORIZE_CODE_REDIS_KEY, key = "'CACHE_AUTHORIZE_CODE_' + #code")
	public AuthorizeCodeUser put(String code, AuthorizeCodeUser dto) {
		return dto;
	}

	@Cacheable(value = CACHE_AUTHORIZE_CODE_REDIS_KEY, key = "'CACHE_AUTHORIZE_CODE_' + #code", unless = "null==#result")
	public AuthorizeCodeUser load(String code) {
		return null;
	}

	@CacheEvict(value = CACHE_AUTHORIZE_CODE_REDIS_KEY, key = "'CACHE_AUTHORIZE_CODE_' + #code")
	public void remove(String code) {
	}

}
