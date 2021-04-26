package com.neusoft.sl.si.authserver.uaa.filter.captcha.domain.sms;

import java.util.HashMap;
import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * SmCaptchaRepository 内存实现版本
 * 
 * <pre>
 * 这里利用缓存的特性支持分布式扩展，否则(如果不部署缓存)就只支持本机登录
 * </pre>
 * 
 * @author mojf
 * 
 */
@Component
public class MemorySmCaptchaRepositoryImpl implements SmCaptchaRepository {

    /**
     * 内存map
     */
    private Map<String, SmCaptcha> captchaMap = new HashMap<String, SmCaptcha>();

    private static final String CACHE_SM_CAPTCHA = "CACHE_SM_CAPTCHA";

    // @Resource
    // private RedisTemplate redisTemplate;

    /*
     * (non-Javadoc)
     * 
     * @see com.neusoft.sl.saber.authserver.domain.model.captcha.sm.
     * SmCaptchaRepository#save(com.neusoft.sl.saber.authserver.domain.model.
     * captcha.sm.SmCaptcha)
     */
    @Override
    @CachePut(value = CACHE_SM_CAPTCHA, key = "#smCaptcha.getMobilenumber()")
    public SmCaptcha save(SmCaptcha smCaptcha) {

//        captchaMap.put(smCaptcha.getMobilenumber(), smCaptcha);
        return smCaptcha;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.neusoft.sl.saber.authserver.domain.model.captcha.sm.
     * SmCaptchaRepository#getCaptcha(java.lang.String)
     */
    @Override
    @Cacheable(value = CACHE_SM_CAPTCHA, key = "#mobilenumber", unless = "null==#result")
    public SmCaptcha getCaptcha(String mobilenumber) {
        return captchaMap.get(mobilenumber);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.neusoft.sl.saber.authserver.domain.model.captcha.sm.
     * SmCaptchaRepository#remove(java.lang.String)
     */
    @CacheEvict(value = CACHE_SM_CAPTCHA, key = "#mobilenumber")
    public void remove(String mobilenumber) {
        captchaMap.remove(mobilenumber);
    }

}
