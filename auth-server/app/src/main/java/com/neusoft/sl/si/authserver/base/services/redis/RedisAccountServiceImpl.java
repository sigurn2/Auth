package com.neusoft.sl.si.authserver.base.services.redis;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.neusoft.sl.si.authserver.base.domains.user.User;
import com.neusoft.sl.si.authserver.base.domains.user.UserRepository;
import com.neusoft.sl.si.authserver.uaa.controller.user.dto.UserAssembler;

/**
 * 
 * @author y_zhang.neu
 *
 */
@Service
public class RedisAccountServiceImpl implements RedisAccountService {
	private static final String USER_ALLOWED = "_USER_ALLOWED";
	/** 日志 */
	private static Logger LOGGER = LoggerFactory.getLogger(RedisAccountServiceImpl.class);

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Resource
	private UserRepository userRepository;

	public void modifyRedis(String cacheKey) {
		if (redisTemplate.hasKey(cacheKey)) {
			LOGGER.warn("处理认证Redis缓存，cacheKey=" + cacheKey);
			redisTemplate.delete(cacheKey);
		}
		String cacheAllowedKey = cacheKey + USER_ALLOWED;
		LOGGER.warn("处理认证Redis缓存，cacheKey=" + cacheAllowedKey);
		if (redisTemplate.hasKey(cacheAllowedKey)) {
			redisTemplate.delete(cacheAllowedKey);
		}
		User user = userRepository.findByAccount(cacheKey);
		if (user != null) {
			// 更新平行权限数据
			ValueOperations<String, Object> ops = redisTemplate.opsForValue();
			// 24个小时过期
			ops.set(cacheAllowedKey, UserAssembler.toAllowedDTO(user), 24, TimeUnit.HOURS);
		}
	}

	@Override
	public void modifyOnlyRedisAccount(String cacheKey) {
		if (redisTemplate.hasKey(cacheKey)) {
			LOGGER.warn("处理认证Redis缓存，cacheKey=" + cacheKey);
			redisTemplate.delete(cacheKey);
		}
	}
}
