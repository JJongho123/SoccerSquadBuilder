package ssb.soccer.com.interceptor;

import ssb.soccer.com.exception.CustomApiException;
import ssb.soccer.com.exception.ExceptionEnum;
import ssb.soccer.user.auth.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class CommonInterceptor implements HandlerInterceptor {

    private final SessionService sessionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String sessionId = request.getHeader("sessionId");

        // 세션 ID가 없는 경우 401 응답
        if (sessionId == null || sessionId.isEmpty()) {
            throw new CustomApiException(ExceptionEnum.UNAUTHORIZED_EXCEPTION, "Session ID가 비어 있습니다.");
        }

        // 세션 ID가 잘못된 경우 401 응답
        if (!sessionService.isSessionValid(sessionId)) {
            throw new CustomApiException(ExceptionEnum.UNAUTHORIZED_EXCEPTION, "잘못된 Session ID입니다.");
        }

        return true;
    }

}

