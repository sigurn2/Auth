package com.neusoft.ehrss.liaoning.security.password.idnumbername;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * redis Service实现
 *
 * @author y_zhang.neu
 */
@Service(value = "RedisService")
public class RedisServiceImpl implements RedisService {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 设置key value
     *
     * @param key
     * @param value
     * @param timeout  超时时间 timeout
     * @param timeUnit 时间单位
     * @return void
     */
    @Override
    public void set(String key, Object value, long timeout, TimeUnit timeUnit) {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        if (!redisTemplate.hasKey(key)) {
            ops.set(key, value, timeout, timeUnit);
        }
    }

    /**
     * 获取key对应的value
     *
     * @param key
     * @return Object
     */
    @Override
    public Object get(String key) {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        if (redisTemplate.hasKey(key)) {
            return ops.get(key);
        } else {
            return null;
        }
    }

    /**
     * 指定key设置超时时间
     *
     * @param key
     * @param expire 设置超时时间  单位秒
     * @return boolean
     */
    @Override
    public void expire(String key, long expire, TimeUnit timeUnit) {
        if (redisTemplate.hasKey(key)) {
            redisTemplate.expire(key, expire, timeUnit);
        }
    }

    /**
     * 指定key是否存在
     *
     * @param key
     * @return boolean
     */
    @Override
    public boolean hasKey(String key) {
        if (redisTemplate.hasKey(key)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据指定key删除数据
     *
     * @param key
     * @return boolean
     */
    @Override
    public void delete(String key) {
        if (redisTemplate.hasKey(key)) {
            redisTemplate.delete(key);
        }
    }
}
