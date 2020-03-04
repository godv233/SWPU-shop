package com.swpu.shop.shopflash.intercepter;


import com.swpu.shop.entity.MallUser;

/**
 * 将用户信息保存在threadLocal中
 *
 * @author 曾伟
 * @date 2019/11/27 16:29
 */
public class UserContext {
    private static ThreadLocal<MallUser> userHolder = new ThreadLocal<>();

    public static void setUser(MallUser user) {
        userHolder.set(user);
    }

    public static MallUser getUser() {
        return userHolder.get();
    }

}
