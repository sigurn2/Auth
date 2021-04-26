package com.neusoft.sl.si.authserver.base.services.redis;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.neusoft.ehrss.liaoning.security.implicit.wechat.WxUserDTO;

@Service
public class WxUserRedisService {

	private static final String CACHE_WX_USER_REDIS_KEY = "CACHE_WX_USER";

	@CachePut(value = CACHE_WX_USER_REDIS_KEY, key = "'CACHE_WX_USER_' + #dto.getOpenid()")
	public WxUserDTO put(WxUserDTO dto) {
		return dto;
	}

	@Cacheable(value = CACHE_WX_USER_REDIS_KEY, key = "'CACHE_WX_USER_' + #openid", unless = "null==#result")
	public WxUserDTO load(String openid) {
		return null;
	}

	@CacheEvict(value = CACHE_WX_USER_REDIS_KEY, key = "'CACHE_WX_USER_' + #openid")
	public void remove(String openid) {
	}

}
