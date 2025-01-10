package com.soccer.com.interceptor;

import com.soccer.session.service.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class CommonIntercerptor implements HandlerInterceptor {

    private final SessionService sessionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String sessionId = request.getHeader("sessionId");

        // 2-1. 세션 ID가 없으면 401 응답
        if (sessionId == null || sessionId.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Session ID is empty");
            return false;
        }

        // 2-2. 세션 ID가 잘못된 경우 401 응답
        if (!sessionService.isSessionValid(sessionId)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Session ID 잘못됨");
            return false;
        }

        return true;
    }

}

