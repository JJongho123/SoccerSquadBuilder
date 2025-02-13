package ssb.soccer.com.interceptor;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import ssb.soccer.com.exception.CustomApiException;
import ssb.soccer.com.exception.ExceptionEnum;
import ssb.soccer.redis.service.RedisService;
import ssb.soccer.user.service.AuthService;
import ssb.soccer.user.service.SessionService;

@Component
@RequiredArgsConstructor
public class CommonInterceptor implements HandlerInterceptor {

    private final AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 쿠키에서 SESSION ID 가져오기
        String sessionId = authService.getCookieSessionId(request);

        // 세션 ID 유효성 검사
        authService.validateSessionId(sessionId);

        return true;
    }

}

