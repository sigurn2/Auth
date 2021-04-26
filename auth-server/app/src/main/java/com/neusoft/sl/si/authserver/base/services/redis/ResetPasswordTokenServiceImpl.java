package com.neusoft.sl.si.authserver.base.services.redis;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.neusoft.sl.si.authserver.uaa.controller.user.dto.ResetPasswordForTokenDTO;

@Service
public class ResetPasswordTokenServiceImpl implements ResetPasswordTokenService {

	private static final String CACHE_RESET_PASSWORD = "CACHE_RESET_PASSWORD";

	@CachePut(value = CACHE_RESET_PASSWORD, key = "'CACHE_RESET_PASSWORD_' + #dto.getToken()")
	public ResetPasswordForTokenDTO saveResetPassword(ResetPasswordForTokenDTO dto) {
		return dto;
	}

	@Cacheable(value = CACHE_RESET_PASSWORD, key = "'CACHE_RESET_PASSWORD_' + #token", unless = "null==#result")
	public ResetPasswordForTokenDTO getResetPassword(String token) {
		return null;
	}

	@CacheEvict(value = CACHE_RESET_PASSWORD, key = "'CACHE_RESET_PASSWORD_' + #token")
	public void remove(String token) {
	}

}
