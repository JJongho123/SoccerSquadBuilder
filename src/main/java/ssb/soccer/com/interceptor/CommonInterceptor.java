package ssb.soccer.com.interceptor;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import ssb.soccer.com.exception.CustomApiException;
import ssb.soccer.com.exception.ExceptionEnum;
import ssb.soccer.user.service.SessionService;

@Component
@RequiredArgsConstructor
public class CommonInterceptor implements HandlerInterceptor {

    private final SessionService sessionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String sessionId = null;
        String key = "user";

        // 쿠키에서 SESSION ID 가져오기
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("sessionId".equals(cookie.getName())) {
                    sessionId = cookie.getValue();
                    break;
                }
            }
        }

        // 세션 ID가 없는 경우 401 응답
        if (sessionId == null || sessionId.isEmpty()) {
            throw new CustomApiException(ExceptionEnum.UNAUTHORIZED_EXCEPTION, "Session ID가 비어 있습니다.");
        }

        // 세션 ID가 잘못된 경우 401 응답
        if (!sessionService.isSessionValid(key, sessionId)) {
            throw new CustomApiException(ExceptionEnum.UNAUTHORIZED_EXCEPTION, "잘못된 Session ID입니다.");
        }

        return true;
    }

}

