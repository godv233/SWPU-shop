package com.swpu.shop.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author 曾伟 zengwei233@126.com
 * @date 2020/3/4 10:07
 */
public class CookieUtil {
    /**
     * 在cookie中取值
     * @param request
     * @param cookieNameToken
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String cookieNameToken) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length <= 0) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (StringUtils.equals(cookie.getName(), cookieNameToken)) {
                return cookie.getValue();
            }
        }
        return null;
    }

}
