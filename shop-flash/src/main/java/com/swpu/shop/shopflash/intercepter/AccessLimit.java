package com.swpu.shop.shopflash.intercepter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 接口限流的注解
 *
 * @author 曾伟
 * @date 2019/11/27 16:14
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AccessLimit {
    //过期时间
    int seconds();
    //在过期时间中的最大访问次数
    int maxCount();
    //是否需要登录
    boolean needLogin() default true;
}
