package com.swpu.shop.redis;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * redis相关的操作：增删改查
 *
 * @author 曾伟
 * @date 2019/11/16 10:09
 */
@Service
public class RedisService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * set
     *
     * @param keyPrefix
     * @param key
     * @param value
     * @return
     */
    public boolean set(KeyPrefix keyPrefix, String key, Object value) {
        boolean result = false;
        String realKey = keyPrefix.getPrefix() + key;
        try {
            redisTemplate.opsForValue().set(realKey, value);
            if (keyPrefix.expireSeconds() > 0) {
                redisTemplate.expire(realKey, keyPrefix.expireSeconds(), TimeUnit.SECONDS);
            }
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 通过key，get对象
     */
    public Object get(KeyPrefix prefix, String key) {
        Object result = null;
        //真正的key
        if (StringUtils.isEmpty(key))
            return null;
        String realKey = prefix.getPrefix() + key;
        try {
            result = redisTemplate.opsForValue().get(realKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 减1
     *
     * @param prefix
     * @param key
     * @return
     */
    public long decrease(KeyPrefix prefix, String key) {
        String realKey = prefix.getPrefix() + key;
        if (StringUtils.isEmpty(realKey)) return 0;
        long result = 0;
        try {
            result = redisTemplate.opsForValue().decrement(realKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean exists(KeyPrefix prefix, String key) {
        String realKey = prefix.getPrefix() + key;
        if (StringUtils.isEmpty(realKey)) return false;
        return redisTemplate.hasKey(realKey);
    }

    /**
     * 删除
     *
     * @param prefix
     * @param s
     */
    public boolean delete(KeyPrefix prefix, String key) {
        String realKey = prefix.getPrefix() + key;
        Boolean delete = redisTemplate.delete(realKey);
        return delete;

    }

    /**
     * 加1
     *
     * @param prefix
     * @param key
     */
    public void incr(KeyPrefix prefix, String key) {
        String realKey = prefix.getPrefix() + key;
        redisTemplate.opsForValue().increment(realKey);
    }

    /**
     * 限流策略：使用zset
     * @param prefix
     * @param key
     * @param period
     * @param maxCount
     * @return
     */
    public boolean isAllowed(KeyPrefix prefix,String key,int period,int maxCount) {
        String realKey=prefix.getPrefix()+key;
        long now=System.currentTimeMillis();
        redisTemplate.opsForZSet().add(realKey,now,now);
        redisTemplate.opsForZSet().removeRangeByScore(realKey,now,now-period*1000);
        Long count = redisTemplate.opsForZSet().zCard(realKey);
        redisTemplate.expire(realKey,period+1,TimeUnit.SECONDS);
        return count<maxCount;
    }


}
