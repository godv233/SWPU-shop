package com.swpu.shop.shopflash.config;

import com.swpu.shop.common.Constants;
import com.swpu.shop.shopflash.intercepter.FlashLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author 曾伟 zengwei233@126.com
 * @date 2020/3/4 9:49
 */
@Configuration
public class FlashWebMvcConfigurer  implements WebMvcConfigurer {
    @Autowired
    private FlashLoginInterceptor flashLoginInterceptor;
    @Autowired
    private UserArgumentResolver userArgumentResolver;

    /**
     * 登录拦截
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(flashLoginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login");
    }
    /**
     * 由于我们自动写了一个WebConfig的配置类，会让springboot的自动配置失效，需要我们自己写静态资源的映射器。
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/upload/**").addResourceLocations("file:" + Constants.FILE_UPLOAD_DIC);
        registry.addResourceHandler("/goods-img/**").addResourceLocations("file:" + Constants.FILE_UPLOAD_DIC);
    }
    /**
     * 注册参数解析器
     * @param resolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userArgumentResolver);
    }
}
