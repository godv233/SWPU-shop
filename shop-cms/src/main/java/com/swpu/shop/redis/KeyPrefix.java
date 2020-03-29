package com.swpu.shop.redis;

/**
 * key的接口
 *
 * @author GODV
 */
public interface KeyPrefix {
    /**
     * 过期时间
     *
     * @return
     */
    int expireSeconds();

    /**
     * 前缀
     *
     * @return
     */
    String getPrefix();

}
