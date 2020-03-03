package com.swpu.shop.redis;

/**
 * User在redis中的key的一些信息。
 * @author 曾伟
 * @date 2019/11/23 18:58
 */
public class UserKey  extends BasePrefix{
    private static final int TOKEN_EXPIRE=3600*24*2;//两天


    public UserKey(int expireSeconds, String prefix) {
        super(expireSeconds,prefix);
    }
    public static UserKey token=new UserKey(TOKEN_EXPIRE,"token:");
    public static UserKey getById=new UserKey(0,"id:");
}
