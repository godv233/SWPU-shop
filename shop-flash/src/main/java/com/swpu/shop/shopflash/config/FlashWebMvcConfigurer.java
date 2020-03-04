package com.swpu.shop.shopflash.config;

import com.swpu.shop.shopflash.intercepter.FlashLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 曾伟 zengwei233@126.com
 * @date 2020/3/4 9:49
 */
@Configuration
public class FlashWebMvcConfigurer  implements WebMvcConfigurer {
    @Autowired
    private FlashLoginInterceptor flashLoginInterceptor;

    /**
     * 登录拦截
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(flashLoginInterceptor)
                .addPathPatterns("/*")
                .excludePathPatterns("/login");
    }
}
