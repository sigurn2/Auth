package com.neusoft.ehrss.liaoning.security.password.idnumbername;

import java.util.concurrent.TimeUnit;

/**
 * redis操作接口
 *
 * @author y_zhang.neu
 */
public interface RedisService {

    /**
     * 设置key value
     *
     * @param key
     * @param value
     * @param timeout  超时时间  单位HOUR
     * @param timeUnit 时间单位
     * @return void
     */
    void set(String key, Object value, long timeout, TimeUnit timeUnit);


    /**
     * 获取key对应的value
     *
     * @param key
     * @return Object
     */
    Object get(final String key);

    /**
     * 指定key设置超时时间
     *
     * @param key
     * @param expire   设置超时时间  单位秒
     * @param timeUnit 单位
     * @return boolean
     */
    void expire(final String key, long expire, TimeUnit timeUnit);

    /**
     * 指定key是否存在
     *
     * @param key
     * @return boolean
     */
    boolean hasKey(final String key);

    /**
     * 根据指定key删除数据
     *
     * @param key
     * @return boolean
     */
    void delete(final String key);
}
