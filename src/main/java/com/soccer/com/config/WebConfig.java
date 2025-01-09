package com.soccer.com.config;

import com.soccer.com.interceptor.CommonIntercerptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final CommonIntercerptor commonInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(commonInterceptor)
//                .addPathPatterns("/**")                      // 모든 요청에 대해 Interceptor 적용
//                .excludePathPatterns("/login", "/api/login/**"); // 제외할 URL 패턴
//    }
}