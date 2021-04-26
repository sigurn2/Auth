package com.neusoft.sl.si.authserver.uaa.validate.login;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class PasswordErrorService {

	public static final String CACHE_PASSWORD_ERROR_COUNT_REDIS_KEY = "CACHE_PASSWORD_ERROR_COUNT";

	@CachePut(value = CACHE_PASSWORD_ERROR_COUNT_REDIS_KEY, key = "'CACHE_PASSWORD_ERROR_COUNT_' + #username")
	public int put(String username, int count) {
		return count;
	}

	@Cacheable(value = CACHE_PASSWORD_ERROR_COUNT_REDIS_KEY, key = "'CACHE_PASSWORD_ERROR_COUNT_' + #username", unless = "null==#result")
	public int load(String username) {
		return 5;
	}

	@CacheEvict(value = CACHE_PASSWORD_ERROR_COUNT_REDIS_KEY, key = "'CACHE_PASSWORD_ERROR_COUNT_' + #username")
	public void remove(String username) {
	}

}
