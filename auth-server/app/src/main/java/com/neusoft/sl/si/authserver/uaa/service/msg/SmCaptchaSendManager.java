package com.neusoft.sl.si.authserver.uaa.service.msg;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import com.neusoft.sl.si.authserver.uaa.exception.CaptchaErrorException;

/**
 * redis 手动操作类
 * 
 * @author zhou.haidong
 *
 */
@Component
public class SmCaptchaSendManager {

	@Autowired
	private SmCaptchaSendCountService smCaptchaSendCountService;

	@Autowired
	private SmCaptchaSendSpaceService smCaptchaSendSpaceService;

	private static final String CACHE_SM_CAPTCHA_SEND_COUNT_REDIS_KEY = "CACHE_SM_CAPTCHA_SEND_COUNT_";

	/**
	 * 发送次数
	 */
	private int send_count = 10;

	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	public void removeSendCount(String mobile) {
		smCaptchaSendCountService.remove(mobile);
		smCaptchaSendSpaceService.remove(mobile);
	}

	public void verifySendCount(String mobile) {
		// 校验发送次数
		int i = smCaptchaSendCountService.load(mobile);
		if (i >= send_count) {
			Long time = getRedisSendCountTime(mobile);
			if (time > 1) {
				throw new BadCredentialsException("您的操作过于频繁，请" + processTime(time) + "后再试");
			}
		}
		// 校验发送间隔
		String string = smCaptchaSendSpaceService.load(mobile);
		if (string != null) {
			throw new CaptchaErrorException("您的操作过于频繁，请稍后再试");
		}
	}

	public void updateSendCount(String mobile) {
		// 设置发送间隔
		smCaptchaSendSpaceService.put(mobile);

		// 设置发送次数
		int count = smCaptchaSendCountService.load(mobile);
		count++;
		long time = redisTemplate.getExpire(CACHE_SM_CAPTCHA_SEND_COUNT_REDIS_KEY + mobile);
		if (time < 1) {
			smCaptchaSendCountService.put(mobile, count);
		} else {
			put(CACHE_SM_CAPTCHA_SEND_COUNT_REDIS_KEY, mobile, count, time, TimeUnit.SECONDS);
		}
	}

	private void put(String cacheStr, String key, Object value, long timeout, TimeUnit unit) {
		getValueOperations().set(cacheStr + key, value, timeout, unit);
	}

	private ValueOperations<String, Object> getValueOperations() {
		return redisTemplate.opsForValue();
	}

	private String processTime(Long time) {
		if (time > 3600) {
			long tmp = time % 3600;
			return time / 3600 + "小时" + processMinute(tmp);
		}
		return processMinute(time);
	}

	private String processMinute(Long time) {
		return (time % 60 > 0 ? time / 60 + 1 : time / 60) + "分钟";
	}

	private Long getRedisSendCountTime(String mobile) {
		Long time = 0l;
		Long temp = redisTemplate.getExpire(CACHE_SM_CAPTCHA_SEND_COUNT_REDIS_KEY + mobile);
		if (temp != null && temp > 0) {
			time = temp;
		}
		return time;
	}

}
