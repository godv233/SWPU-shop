package com.swpu.shop.shopflash.config;

import com.swpu.shop.entity.MallUser;
import com.swpu.shop.shopflash.intercepter.UserContext;
import com.swpu.shop.shopflash.service.UserService;
import com.swpu.shop.util.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 参数解析器：用户
 *
 * @author 曾伟
 * @date 2019/11/21 15:13
 */
@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    private UserService userService;

    /**
     * 判断参数类型
     *
     * @param methodParameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        //参数的判断，是否是user类型，true才会执行下一步
        Class<?> type = methodParameter.getParameterType();
        return type == MallUser.class;
    }

    /**
     * 从threadLocal中得到user 绑定在controller的参数上
     *
     * @param methodParameter
     * @param modelAndViewContainer
     * @param nativeWebRequest
     * @param webDataBinderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
//        //从redis中读取user.
//        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
//        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
//
//        String paramToken = request.getParameter("token");
//        String cookieToken = CookieUtil.getCookieValue(request, "token");
//
//        //以此判断两种方式传入token
//        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
//            return null;
//        }
//        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
//        MallUser user = userService.getUserByToken(response, token);
//        return user;
        return UserContext.getUser();
    }
}
