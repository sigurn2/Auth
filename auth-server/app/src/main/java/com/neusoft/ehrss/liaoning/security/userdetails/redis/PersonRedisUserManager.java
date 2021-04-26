package com.neusoft.ehrss.liaoning.security.userdetails.redis;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class PersonRedisUserManager {

	private static final String CACHE_PERSONREDIS_USER_REDIS_KEY = "CACHE_PERSONREDIS_USER_REDIS";

	@CachePut(value = CACHE_PERSONREDIS_USER_REDIS_KEY, key = "'CACHE_PERSONREDIS_USER_REDIS_' + #personRedisUser.getIdNumber()")
	public PersonRedisUser putByIdNumber(PersonRedisUser personRedisUser) {
		return personRedisUser;
	}

	@CachePut(value = CACHE_PERSONREDIS_USER_REDIS_KEY, key = "'CACHE_PERSONREDIS_USER_REDIS_' + #personRedisUser.getPersonNumber()")
	public PersonRedisUser putByPersonNumber(PersonRedisUser personRedisUser) {
		return personRedisUser;
	}

	@Cacheable(value = CACHE_PERSONREDIS_USER_REDIS_KEY, key = "'CACHE_PERSONREDIS_USER_REDIS_' + #idNumber", unless = "null==#result")
	public PersonRedisUser load(String idNumber) {
		return null;
	}

	@CacheEvict(value = CACHE_PERSONREDIS_USER_REDIS_KEY, key = "'CACHE_PERSONREDIS_USER_REDIS_' + #idNumber")
	public void remove(String idNumber) {
	}

}
