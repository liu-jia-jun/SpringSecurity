package com.example.jwt.config;

import com.example.jwt.Interceptor.JWTInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 刘佳俊
 */
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JWTInterceptor())
                .addPathPatterns("/test")// 拦截验证的接口
                .excludePathPatterns("/login");// 放行的接口
    }
}
