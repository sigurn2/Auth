package com.neusoft.sl.si.authserver.uaa.validate.login;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

/**
 * redis 手动操作类
 * 
 * @author zhou.haidong
 *
 */
@Component
public class PasswordErrorRedisManager {

	public static final String CACHE_PASSWORD_ERROR_COUNT_REDIS_KEY = "CACHE_PASSWORD_ERROR_COUNT_";

	@Autowired
	private PasswordErrorService passwordErrorService;

	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	public void removePasswordErrorCount(String key) {
		passwordErrorService.remove(key);
	}

	public void removePasswordErrorCount(String key, String key2) {
		passwordErrorService.remove(key);
		passwordErrorService.remove(key2);
	}

	public void verifyPasswordErrorCount(String key) {
		Long time = getRedisErrorTime(key);
		if (time > 1) {
			throw new BadCredentialsException("您的操作过于频繁，请" + processMinute(time) + "后再试");
		}
	}

	public void verifyPasswordErrorCount(String key, String key2) {
		Long time = getRedisErrorTime(key);
		Long time2 = getRedisErrorTime(key2);

		Long rs_time = time;
		if (time < time2) {
			rs_time = time2;
		}
		if (rs_time > 1) {
			throw new BadCredentialsException("您的操作过于频繁，请" + processMinute(rs_time) + "后再试");
		}
	}

	private Long getRedisErrorTime(String key) {
		Long time = 0l;
		int count = passwordErrorService.load(key);
		if (count <= 0) {
			time = redisTemplate.getExpire(CACHE_PASSWORD_ERROR_COUNT_REDIS_KEY + key);
		}
		return time;
	}

	public void updatePasswordErrorCount(String key, String key2) {
		int count = updateRedisErrorCount(key);
		int count2 = updateRedisErrorCount(key2);

		int rs_count = count;
		if (count > count2) {
			rs_count = count2;
		}
		if (rs_count > 0) {
			throw new BadCredentialsException("账号或密码有误，您还有" + rs_count + "次机会");
		} else {
			passwordErrorService.put(key, rs_count);
			passwordErrorService.put(key2, rs_count);
			throw new BadCredentialsException("您的操作过于频繁，请30分钟后再试");
		}
	}

	public void updatePasswordErrorCount(String key) {
		int count = updateRedisErrorCount(key);
		if (count > 0) {
			throw new BadCredentialsException("账号或密码有误，您还有" + count + "次机会");
		} else {
			passwordErrorService.put(key, count);
			throw new BadCredentialsException("您的操作过于频繁，请30分钟后再试");
		}
	}

	private int updateRedisErrorCount(String key) {
		int count = passwordErrorService.load(key);
		count--;
		long time = redisTemplate.getExpire(CACHE_PASSWORD_ERROR_COUNT_REDIS_KEY + key);
		if (time < 1) {
			passwordErrorService.put(key, count);
		} else {
			put(CACHE_PASSWORD_ERROR_COUNT_REDIS_KEY, key, count, time, TimeUnit.SECONDS);
		}
		return count;
	}

	private void put(String cacheStr, String key, Object value, long timeout, TimeUnit unit) {
		getValueOperations().set(cacheStr + key, value, timeout, unit);
	}

	private ValueOperations<String, Object> getValueOperations() {
		return redisTemplate.opsForValue();
	}

	private String processMinute(Long time) {
		return (time % 60 > 0 ? time / 60 + 1 : time / 60) + "分钟";
	}

}
