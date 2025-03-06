package ssb.soccer.com.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ssb.soccer.com.interceptor.CommonInterceptor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final CommonInterceptor commonInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(commonInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/login",
                        "/login/**",
                        "/html/login.html",
                        "/register",
                        "/register/**",
                        "/html/register.html",
                        "/api/auth/login/**",
                        "/static/**",
                        "/css/**",
                        "/js/**",
                        "/images/**",
                        "/api/validation/password-policies",
                        "/favicon.ico",
                        "/api/user/**",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "http://10.100.10.132:8080/**"
                );
    }

}