package ssb.soccer.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ssb.soccer.com.constant.CommonConstant;
import ssb.soccer.com.encrypt.EncryptionService;
import ssb.soccer.com.exception.CustomApiException;
import ssb.soccer.com.exception.ExceptionEnum;
import ssb.soccer.com.util.CookieUtil;
import ssb.soccer.passwdPolicy.service.PasswdVaildationService;
import ssb.soccer.redis.service.RedisService;
import ssb.soccer.user.dto.UserWithTeamDTO;
import ssb.soccer.user.mapper.UserMapper;
import ssb.soccer.user.model.User;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 사용자 관련 비즈니스 로직을 처리하는 서비스 클래스.
 * - 사용자 조회, 생성, 업데이트 기능을 제공한다.
 * - 비밀번호 검증 및 암호화 기능을 포함한다.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final PasswdVaildationService passwdVaildationService;
    private final EncryptionService encryptionService;
    private final RedisService redisService;

    /**
     * 모든 사용자를 조회한다.
     *
     * @return 사용자 목록
     */
    public List<User> getAllUsers() {
        return userMapper.findAllUsers();
    }

    /**
     * 새로운 사용자를 생성한다.
     * - 비밀번호 정책을 검증한 후, 비밀번호를 암호화하여 저장한다.
     *
     * @param user 생성할 사용자 객체
     * @throws CustomApiException 비밀번호 정책 위반 시 예외 발생
     * @throws CustomApiException 사용자 생성 실패 시 예외 발생
     */
    public void createUser(User user) {
        // 비밀번호 정책 검증
        if (!passwdVaildationService.isPolicySatisfaction(user.getPasswd())) {
            throw new CustomApiException(ExceptionEnum.PASSWD_POLICY_ERROR);
        }

        // 비밀번호 암호화
        Map<String, String> encryptedData = encryptionService.generateHashPassWordAndSalt(user.getPasswd());
        user.setSalt(encryptedData.get("salt"));
        user.setPasswd(encryptedData.get("password"));

        // 사용자 저장
        boolean isCreated = userMapper.createUser(user);
        if (!isCreated) {
            throw new CustomApiException(ExceptionEnum.USER_CREATION_FAILED);
        }
    }

    /**
     * 사용자 ID를 이용하여 사용자를 조회한다.
     *
     * @param userId 조회할 사용자 ID
     * @return 해당 ID를 가진 사용자 객체
     */
    public User findById(String userId) {
        return userMapper.findByUserId(userId);
    }

    /**
     * 사용자 ID를 이용하여 사용자의 팀 정보를 포함한 정보를 조회한다.
     *
     * @param userId 조회할 사용자 ID
     * @return 사용자 및 소속 팀 정보 DTO
     */
    public UserWithTeamDTO findUserWithTeam(String userId) {
        return userMapper.findUserWithTeam(userId);
    }


    /**
     * 현재 세션을 기반으로 사용자 정보를 조회한다. (Look-aside 캐싱 적용)
     * - Redis에서 사용자 정보를 먼저 조회(Cache Hit).
     * - 캐시에 없으면(Cache Miss), DB에서 조회한 후 Redis에 저장.
     *
     * @param request HTTP 요청 객체 (쿠키에서 세션 ID 추출)
     * @return 현재 로그인된 사용자 정보 (세션 기반)
     * @throws JsonProcessingException JSON 변환 오류 발생 시 예외 발생
     */
    public UserWithTeamDTO getUserInfo(HttpServletRequest request) throws JsonProcessingException {
        String sessionId = CookieUtil.getCookieSessionId(request);
        UserWithTeamDTO user = redisService.getHashOpsAsObject(CommonConstant.USER_KEY, sessionId, UserWithTeamDTO.class);
        if  (user == null) {
            user = userMapper.findUserWithTeam(sessionId);
            redisService.storeUserInRedis(sessionId, user);
        }
        return user;
    }

    /**
     * 사용자 ID를 이용하여 사용자를 조회한다.
     *
     * @param id 조회할 사용자 ID
     * @return 해당 ID를 가진 사용자 객체
     */
    public User getUserById(int id) {
        return userMapper.findById(id);
    }

    /**
     * 사용자 정보를 업데이트한다.
     * - 변경된 사용자 정보를 데이터베이스에 반영한다.
     *
     * @param user 업데이트할 사용자 객체
     */
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }
}
