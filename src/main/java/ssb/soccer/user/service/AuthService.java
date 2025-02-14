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
import ssb.soccer.com.util.CookieUtil;
import ssb.soccer.redis.service.RedisService;
import ssb.soccer.user.dto.LoginRequestDto;
import ssb.soccer.user.dto.UserWithTeamDTO;
import ssb.soccer.user.model.User;

import java.time.Duration;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final EncryptionService encryptionService;
    private final RedisService redisService;

    public Cookie login(LoginRequestDto loginDto) throws JsonProcessingException {

        String userId = loginDto.getUserId();
        String inputPwd = loginDto.getPasswd();

        UserWithTeamDTO user = userService.findUserWithTeam(userId);
        if (user != null) {
            boolean isAuthenticated = encryptionService.verifyPassword(inputPwd, user.getPasswd(), user.getSalt());
            if (isAuthenticated) {

                ObjectMapper objectMapper = new ObjectMapper();
                HashMap<String, Object> data = new HashMap<>();

                // pw, salt 제거 후 Redis에 저장
                user.setSalt(null);
                user.setPasswd(null);

                data.put(userId, objectMapper.writeValueAsString(user));

                // Redis에 사용자 정보 저장
                redisService.setHashOps(CommonConstant.USER_KEY, data, Duration.ofSeconds(CommonConstant.EXPIRY_DURATION_SECONDS));

                // 로그인 성공 시 쿠키 생성
                return CookieUtil.createSessionCookie(userId);
            }
        }
        else{
            throw new CustomApiException(ExceptionEnum.USER_NOT_FOUND_EXCEPTION);
        }
        return null;
    }

    public void validateSessionId(String sessionId){

        // 세션 ID가 없는 경우 401 응답
        if (sessionId == null || sessionId.isEmpty()) {
            throw new CustomApiException(ExceptionEnum.UNAUTHORIZED_EXCEPTION, "Session ID가 비어 있습니다.");
        }

        // 세션 ID가 잘못된 경우 401 응답
        if (!redisService.existsHashKey(CommonConstant.USER_KEY, sessionId)) {
            throw new CustomApiException(ExceptionEnum.UNAUTHORIZED_EXCEPTION, "잘못된 Session ID입니다.");
        }

    }
}

