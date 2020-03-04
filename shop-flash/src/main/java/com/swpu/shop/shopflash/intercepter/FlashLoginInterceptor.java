package com.swpu.shop.shopflash.intercepter;

import com.swpu.shop.entity.MallUser;
import com.swpu.shop.shopflash.redis.RedisService;
import com.swpu.shop.shopflash.service.UserService;
import com.swpu.shop.util.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 秒杀登录拦截
 * @author 曾伟 zengwei233@126.com
 * @date 2020/3/4 9:44
 */
@Component
public class FlashLoginInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断登录 成功返回true 失败跳转登陆页面。
        MallUser user = getUser(request, response);
        if (user==null){
            //跳转登录
            response.sendRedirect("http://localhost:8080/login");
            return false;
        }else{
            UserContext.setUser(user);
            return true;
        }
    }
    /**
     * 通过请求中的token得到user，可检验登录
     * @param request
     * @param response
     * @return
     */
    public MallUser getUser(HttpServletRequest request, HttpServletResponse response){
        String paramToken=request.getParameter("token");
        String cookieToken= CookieUtil.getCookieValue(request,"token");
        //以此判断两种方式传入token
        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
            return null;
        }
        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
        MallUser user = userService.getUserByToken(response, token);
        return user;
    }
}
