package com.swpu.shop.shopflash.intercepter;

import com.swpu.shop.entity.MallUser;
import com.swpu.shop.shopflash.common.CodeMsg;
import com.swpu.shop.shopflash.common.Result;
import com.swpu.shop.shopflash.redis.AccessKey;
import com.swpu.shop.shopflash.redis.RedisService;
import com.swpu.shop.shopflash.service.UserService;
import com.swpu.shop.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * 拦截器
 *
 * @author 曾伟
 * @date 2019/11/27 16:18
 */
@Service
public class AccessInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisService redisService;

    /**
     * 拦截器：方法执行之前
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
            if (accessLimit == null) {
                return true;
            } else {
                MallUser user = UserContext.getUser();
                int seconds = accessLimit.seconds();
                int maxCount = accessLimit.maxCount();
                //将判断登录的逻辑可以放在这里
                boolean needLogin = accessLimit.needLogin();
                String key = request.getRequestURI();
                if (needLogin) {
                    if (user == null) {
                        //返回前端页面一些错误的信息
                        render(response, CodeMsg.SESSION_ERROR);
                        return false;
                    }
                    key += "_" + user.getUserId();
                    //限流的核心代码
                    boolean allowed = redisService.isAllowed(AccessKey.withExpire(seconds), key, seconds, maxCount);
                    if (!allowed){
                        render(response,CodeMsg.ACCESS_LIMIT_REACHED);
                    }
                    return allowed;
                }
            }
        }
        return true;
    }

    /**
     * 在拦截器中抛出异常给前台
     *
     * @param response
     * @param sessionError
     */
    private void render(HttpServletResponse response, CodeMsg sessionError) throws Exception {
        response.setContentType("application/json;charset=utf-8");
        OutputStream out = response.getOutputStream();
        String str = JsonUtil.objToJson(Result.error(sessionError));
        out.write(str.getBytes("UTF-8"));
        out.flush();
        out.close();

    }
}
