package com.swpu.shop.shopflash.redis;

/**
 * 所有key的父类
 *
 * @author 曾伟
 * @date 2019/11/23 19:00
 */
public abstract class BasePrefix  implements KeyPrefix{
    private int expireSeconds;//过期时间
    private String prefix;//前缀

    public BasePrefix(String prefix) {//0代表永不过期
        this(0, prefix);
    }

    public BasePrefix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    public int expireSeconds() {//默认0代表永不过期
        return expireSeconds;
    }

    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className + ":" + prefix;
    }
}
