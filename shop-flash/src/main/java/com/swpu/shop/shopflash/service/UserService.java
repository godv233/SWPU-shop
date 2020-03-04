package com.swpu.shop.shopflash.service;

import com.swpu.shop.entity.MallUser;
import com.swpu.shop.shopflash.redis.RedisService;
import com.swpu.shop.shopflash.redis.UserKey;
import com.swpu.shop.util.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 曾伟 zengwei233@126.com
 * @date 2020/3/4 10:13
 */
@Service
public class UserService {
    @Autowired
    private RedisService redisService;

    public MallUser getUserByToken(HttpServletResponse response, String token){
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        //得到user对象
        MallUser user = (MallUser) redisService.get(UserKey.token, token);
        //更改过期时间：就是重新设置一下cookie.
        if (user != null) {
            addCookie(user, token, response);
        }
        return user;
    }

    /**
     * 生成一个cookie
     * @param user
     * @param token
     * @param response
     */
    private void addCookie(MallUser user, String token, HttpServletResponse response) {
        redisService.set(UserKey.token, token, user);
        Cookie cookie = new Cookie("token", token);
        cookie.setMaxAge(UserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
