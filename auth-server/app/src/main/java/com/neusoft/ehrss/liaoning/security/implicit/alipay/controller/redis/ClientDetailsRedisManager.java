package com.neusoft.ehrss.liaoning.security.implicit.alipay.controller.redis;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.ClientDetailsUserDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;

@Service
public class ClientDetailsRedisManager {
	
	@Autowired
	private DataSource dataSource;
	
	private ClientDetailsUserDetailsService clientDetailsUserDetailsService;

	@PostConstruct
	public void init() {
		clientDetailsUserDetailsService = new ClientDetailsUserDetailsService(clientDetailsService());
	}

	public static final String CACHE_CLIENT_ID_REDIS_KEY = "CACHE_CLIENT_ID";


	@Cacheable(value = CACHE_CLIENT_ID_REDIS_KEY, key = "'CACHE_CLIENT_ID_' + #clientId", unless = "null==#result")
	public UserDetails load(String clientId) {
		UserDetails userDetails = clientDetailsUserDetailsService.loadUserByUsername(clientId);
		return userDetails;
	}

	@CacheEvict(value = CACHE_CLIENT_ID_REDIS_KEY, key = "'CACHE_CLIENT_ID_' + #clientId")
	public void remove(String clientId) {
	}
	
	private ClientDetailsService clientDetailsService() {
		return new JdbcClientDetailsService(dataSource);
	}

}
