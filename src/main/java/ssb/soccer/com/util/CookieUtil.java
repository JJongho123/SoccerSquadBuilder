package ssb.soccer.com.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import ssb.soccer.com.constant.CommonConstant;

public class CookieUtil {

    public static String getCookieSessionId(HttpServletRequest request){
        String sessionId = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("sessionId".equals(cookie.getName())) {
                    sessionId = cookie.getValue();
                    break;
                }
            }
        }
        return sessionId;

    }

    public static Cookie createSessionCookie(String sessionId) {
        Cookie sessionCookie = new Cookie("sessionId", sessionId);
        sessionCookie.setHttpOnly(true);
        sessionCookie.setPath("/");
        sessionCookie.setMaxAge(CommonConstant.EXPIRY_DURATION_SECONDS);
        sessionCookie.setAttribute("SameSite", "Strict"); // CSRF 방어
        return sessionCookie;
    }
}
