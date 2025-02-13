package ssb.soccer.user.service;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ssb.soccer.com.constant.CommonConstant;
import ssb.soccer.com.encrypt.EncryptionService;
import ssb.soccer.redis.service.RedisService;
import ssb.soccer.user.model.LoginDto;
import ssb.soccer.user.model.User;

import java.time.Duration;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final EncryptionService encryptionService;
    private final RedisService redisService;

    public Cookie login(LoginDto loginDto) {

        String userId = loginDto.getUserId();
        String inputPwd = loginDto.getPasswd();

        User user = userService.findById(userId);
        if (user != null) {
            boolean isAuthenticated = encryptionService.verifyPassword(inputPwd, user.getPasswd(), user.getSalt());
            if (isAuthenticated) {

                String key = "user";

                HashMap<Object, Object> userData = new HashMap<>();
                userData.put("userId", userId);
                userData.put("salt", user.getSalt());
                userData.put("email", user.getEmail());
                userData.put("name", user.getName());

                HashMap<String, Object> data = new HashMap<>();
                data.put(userId, userData);

                // Redis에 세션 정보 저장
                redisService.setHashOps(key, data, Duration.ofSeconds(CommonConstant.EXPIRY_DURATION_SECONDS));

                System.out.println(redisService.getHashOps(key, userId));

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
}

