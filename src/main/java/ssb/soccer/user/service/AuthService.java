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

import java.time.Duration;
import java.util.HashMap;

/**
 * 사용자 인증(Authentication) 관련 비즈니스 로직을 처리하는 서비스 클래스.
 * 로그인 및 세션 검증 기능을 제공한다.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final EncryptionService encryptionService;
    private final RedisService redisService;

    /**
     * 사용자의 로그인 요청을 처리한다.
     * - 입력된 비밀번호를 검증하고, 인증 성공 시 Redis에 사용자 정보를 저장한다.
     * - 로그인 성공 시 세션 쿠키를 생성하여 반환한다.
     *
     * @param loginDto 로그인 요청 DTO (사용자 ID, 비밀번호 포함)
     * @return 로그인 성공 시 생성된 세션 쿠키
     * @throws JsonProcessingException JSON 변환 중 오류 발생 시 예외 발생
     * @throws CustomApiException 사용자가 존재하지 않거나 인증 실패 시 예외 발생
     */
    public Cookie login(LoginRequestDto loginDto, HttpServletRequest request) throws JsonProcessingException {

        String userId = loginDto.getUserId();
        String inputPwd = loginDto.getPasswd();

        UserWithTeamDTO user = userService.getUserInfo(request, userId);
        if (user != null) {
            boolean isAuthenticated = encryptionService.verifyPassword(inputPwd, user.getPasswd(), user.getSalt());
            if (isAuthenticated) {
                // 로그인 성공 시 세션 쿠키 생성
                return CookieUtil.createSessionCookie(userId);
            }
        }
        else {
            throw new CustomApiException(ExceptionEnum.USER_NOT_FOUND_EXCEPTION);
        }
        return null;
    }

    /**
     * 세션 ID의 유효성을 검증한다.
     * - 세션 ID가 존재하는지 확인하고, 유효하지 않으면 예외를 발생시킨다.
     *
     * @param sessionId 검증할 세션 ID
     * @throws CustomApiException 세션 ID가 비어 있거나 유효하지 않을 경우 예외 발생
     */
    public void validateSessionId(String sessionId) {

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
