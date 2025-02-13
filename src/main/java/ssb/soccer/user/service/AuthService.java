package ssb.soccer.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ssb.soccer.com.constant.CommonConstant;
import ssb.soccer.com.encrypt.EncryptionService;
import ssb.soccer.com.exception.CustomApiException;
import ssb.soccer.com.exception.ExceptionEnum;
import ssb.soccer.redis.service.RedisService;
import ssb.soccer.user.dto.LoginDto;
import ssb.soccer.user.model.User;

import java.time.Duration;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final EncryptionService encryptionService;
    private final RedisService redisService;

    public Cookie login(LoginDto loginDto) throws JsonProcessingException {

        String userId = loginDto.getUserId();
        String inputPwd = loginDto.getPasswd();

        User user = userService.findById(userId);
        if (user != null) {
            boolean isAuthenticated = encryptionService.verifyPassword(inputPwd, user.getPasswd(), user.getSalt());
            if (isAuthenticated) {

                ObjectMapper objectMapper = new ObjectMapper();
                HashMap<String, Object> data = new HashMap<>();

                String key = "user";

                data.put(userId, objectMapper.writeValueAsString(user));

                // Redis에 세션 정보 저장
                redisService.setHashOps(key, data, Duration.ofSeconds(CommonConstant.EXPIRY_DURATION_SECONDS));

                User test = objectMapper.readValue(redisService.getHashOps(key, userId), User.class);
                System.out.println(test);
                System.out.println(test.getName());
                System.out.println(test.getSalt());

                // 로그인 성공 시 쿠키 생성
                return createSessionCookie(userId);
            }
        }
        return null;
    }

    private Cookie createSessionCookie(String sessionId) {
        Cookie sessionCookie = new Cookie("sessionId", sessionId);
        sessionCookie.setHttpOnly(true);
        sessionCookie.setPath("/");
        sessionCookie.setMaxAge(CommonConstant.EXPIRY_DURATION_SECONDS);
        sessionCookie.setAttribute("SameSite", "Strict"); // CSRF 방어
        return sessionCookie;
    }

    public String getCookieSessionId(HttpServletRequest request){
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

    public void validateSessionId(String sessionId){

        String key = "user";

        // 세션 ID가 없는 경우 401 응답
        if (sessionId == null || sessionId.isEmpty()) {
            throw new CustomApiException(ExceptionEnum.UNAUTHORIZED_EXCEPTION, "Session ID가 비어 있습니다.");
        }

        // 세션 ID가 잘못된 경우 401 응답
        if (!redisService.existsHashKey(key, sessionId)) {
            throw new CustomApiException(ExceptionEnum.UNAUTHORIZED_EXCEPTION, "잘못된 Session ID입니다.");
        }

    }
}

