package com.neusoft.sl.si.authserver.uaa.config.redis;

import java.util.HashMap;
import java.util.Map;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Redis 配置管理
 * 
 * @author mojf
 *
 */
@Configuration
@EnableCaching
public class RedisCacheConfig {
	/** 图片验证码REDIS缓存超时时间 */
	private static final String CACHE_SM_CAPTCHA_REDIS_KEY = "CACHE_SM_CAPTCHA";
	/** 短信验证码REDIS缓存超时时间 */
	private static final String CACHE_IMG_CAPTCHA_REDIS_KEY = "CACHE_IMG_CAPTCHA";
	/** 用户信息缓存REDIS缓存超时时间 */
	private static final String CACHE_USER_REDIS_KEY = "CACHE_USER";
	/** 用户平行权限信息缓存REDIS缓存超时时间 */
	private static final String CACHE_USERALLOWED_REDIS_KEY = "CACHE_USER_ALLOWED";
	/** 用户重置密码缓存REDIS缓存超时时间 */
	private static final String CACHE_RESET_PASSWORD_REDIS_KEY = "CACHE_RESET_PASSWORD";
	/** 用户person信息缓存REDIS缓存超时时间 */
	private static final String CACHE_PERSON_REDIS_KEY = "CACHE_PERSON";
	/** 用户company_si信息缓存REDIS缓存超时时间 */
	private static final String CACHE_COMPANY_SI_REDIS_KEY = "CACHE_COMPANY_SI";
	/**
	 * 缓存openid用户信息
	 */
	private static final String CACHE_WX_USER_REDIS_KEY = "CACHE_WX_USER";
	/**
	 * 用户登录错误次数
	 */
	private static final String CACHE_PASSWORD_ERROR_COUNT_REDIS_KEY = "CACHE_PASSWORD_ERROR_COUNT";

	/**
	 * 短信发送次数限制，4小时10条
	 */
	private static final String CACHE_SM_CAPTCHA_SEND_COUNT_REDIS_KEY = "CACHE_SM_CAPTCHA_SEND_COUNT";

	/**
	 * 短信发送间隔，1分钟
	 */
	private static final String CACHE_SM_CAPTCHA_SEND_SPACE_REDIS_KEY = "CACHE_SM_CAPTCHA_SEND_SPACE";

	/**
	 * idnumber用户信息缓存时间
	 */
	private static final String CACHE_PERSONREDIS_USER_REDIS_KEY = "CACHE_PERSONREDIS_USER_REDIS";

	/**
	 * 授权码缓存
	 */
	public static final String CACHE_AUTHORIZE_CODE_REDIS_KEY = "CACHE_AUTHORIZE_CODE";

	/**
	 * 查询clientid缓存
	 */
	public static final String CACHE_CLIENT_ID_REDIS_KEY = "CACHE_CLIENT_ID";

	@Bean
	public CacheManager cacheManager(@SuppressWarnings("rawtypes") RedisTemplate redisTemplate) {
		RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
		// Number of seconds before expiration. Defaults to unlimited (0)
		// 默认缓存3个小时失效
		cacheManager.setDefaultExpiration(10800);

		@SuppressWarnings("serial")
		Map<String, Long> expires = new HashMap<String, Long>() {
			{
				put(CACHE_SM_CAPTCHA_REDIS_KEY, 300L); // 短信验证码 5分钟
				put(CACHE_IMG_CAPTCHA_REDIS_KEY, 300L); // 图片验证码 5分钟
				put(CACHE_USER_REDIS_KEY, 1800L); // 用户信息30分钟
				put(CACHE_USERALLOWED_REDIS_KEY, 86400L); // 用户信息
				put(CACHE_RESET_PASSWORD_REDIS_KEY, 300L); // 5分钟
				put(CACHE_PERSON_REDIS_KEY, 300L); // person信息5分钟
				put(CACHE_COMPANY_SI_REDIS_KEY, 300L); // company_si信息5分钟
				put(CACHE_PASSWORD_ERROR_COUNT_REDIS_KEY, 1800L); //
				put(CACHE_SM_CAPTCHA_SEND_COUNT_REDIS_KEY, 14400L); //
				put(CACHE_SM_CAPTCHA_SEND_SPACE_REDIS_KEY, 60L); //
				put(CACHE_PERSONREDIS_USER_REDIS_KEY, 43200L); // 12小时
				put(CACHE_AUTHORIZE_CODE_REDIS_KEY, 300L); // 授权码，5分钟
				put(CACHE_CLIENT_ID_REDIS_KEY, 600L); // ，10分钟
				put(CACHE_WX_USER_REDIS_KEY, 600L); // WX信息10分钟
			}
		};

		cacheManager.setExpires(expires);
		return cacheManager;
	}

	/**
	 * Redis缓存
	 * 
	 * @param factory
	 * @return
	 */
	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		redisTemplate.setConnectionFactory(factory);
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}
}
