package ssb.soccer.redis.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import ssb.soccer.com.constant.CommonConstant;
import ssb.soccer.com.exception.CustomApiException;
import ssb.soccer.com.exception.ExceptionEnum;
import ssb.soccer.user.dto.UserWithTeamDTO;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final HashOperations<String, String, Object> hashOperations;
    private final ObjectMapper objectMapper;

    public RedisService(RedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
        this.objectMapper = objectMapper;
    }

    /**
     * Redis 데이터 저장
     *
     * @param	key (type = String)
     * @param	data (type = String)
     */
    public void setValues(String key, String data) {
        ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();
        valueOps.set(key, data);
    }

    /**
     * Redis 데이터 만료시간 설정 저장 (TTL)
     *
     * @param	key (type = String)
     * @param	data (type = String)
     * @param	duration (type = Duration)
     */
    public void setValues(String key, String data, Duration duration) {
        ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();
        valueOps.set(key, data, duration);
    }

    /**
     * Redis 데이터 조회
     *
     * @param	key (type = String)
     */
    public String getValues(String key) {
        ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();
        Object value = valueOps.get(key);
        return value != null ? value.toString() : null;
    }

    /**
     * Redis Hash 타입 데이터 저장
     *
     * @param	key (type = String)
     * @param	data (type = Map<Object, Object>)
     */
    public void setHashOps(String key, Map<String, Object> data) {
        hashOperations.putAll(key, data);
    }

    /**
     * Redis Hash 타입 데이터 저장 및 TTL 설정
     *
     * @param key Redis의 Hash 키
     * @param data 저장할 데이터 (Hash 형태의 Map)
     * @param duration 만료 시간 (Hash 키 전체에 적용)
     */
    public void setHashOps(String key, Map<String, Object> data, Duration duration) {
        hashOperations.putAll(key, data);
        redisTemplate.expire(key, duration);
    }

    /**
     * Redis Hash 타입 데이터 전체 조회
     *
     * @param key Redis의 Hash 키
     * @return Map 형태의 전체 필드-값 쌍
     */
    public Map<String, Object> getAllHashOps(String key) {
        return hashOperations.entries(key);
    }

    /**
     * Redis TTL 조회
     *
     * @param key Redis 키
     * @return TTL (초 단위)
     *         -2: 키가 존재하지 않음
     *         -1: 키가 존재하지만 만료 시간 없음
     */
    public Long getTTL(String key) {
        Long ttl = redisTemplate.getExpire(key, TimeUnit.SECONDS);
        return ttl != null ? ttl : -1;
    }

    /**
     * Redis Hash 타입 데이터 조회
     *
     * @param	key (type = String)
     * @param	hashKey (type = String)
     */
    public String getHashOps(String key, String hashKey) {
        if (!Boolean.TRUE.equals(hashOperations.hasKey(key, hashKey))) {
            return null;
        }
        Object value = hashOperations.get(key, hashKey);
        return value != null ? value.toString() : null;
    }

    /**
     * Redis Hash 타입 데이터 조회 (mapping 클래스 추가)
     *
     * @param	key (type = String)
     * @param	hashKey (type = String)
     * @param	clazz (type = Class<T>)
     *
     */
    public <T> T getHashOpsAsObject(String key, String hashKey, Class<T> clazz) {
        String jsonData = this.getHashOps(key, hashKey);
        if (jsonData == null) {
            return null;
        }

        try {
            return objectMapper.readValue(jsonData, clazz);
        } catch (JsonProcessingException e) {
            throw new CustomApiException(ExceptionEnum.REDIS_DATA_CONVERT_FAILED);
        }
    }

    /**
     * Redis에서 특정 키의 데이터를 삭제
     *
     * @param key Redis의 키
     * @return 삭제 성공 여부 (true: 삭제 성공, false: 삭제 실패)
     */
    public boolean deleteKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    /**
     * Redis Hash 타입 데이터 특정 필드 삭제
     *
     * @param	key (type = String)
     * @param	hashKey (type = String)
     */
    public void deleteHashOps(String key, String hashKey) {
        hashOperations.delete(key, hashKey);
    }

    /**
     * Redis에서 키 존재 여부 확인
     *
     * @param	key (type = String)
     * @return	boolean 키 존재 여부 (true: 키 있음 , false: 키 없음)
     */
    public boolean exists(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * Redis에서 hashKey 존재 여부 확인
     *
     * @param	key (type = String)
     * @param	hashKey (type = String)
     * @return	boolean hashKey 존재 여부 (true: 키 있음 , false: 키 없음)
     */
    public boolean existsHashKey(String key, String hashKey) {
        return Boolean.TRUE.equals(hashOperations.hasKey(key, hashKey));
    }

    /**
     * 사용자 정보를 Redis에 저장하는 공통 메서드.
     * - 세션 ID를 기반으로 데이터를 캐싱
     * - 비밀번호 및 Salt 정보 제거 후 저장
     *
     * @param sessionId 사용자 세션 ID
     * @param user 사용자 정보 객체
     * @throws JsonProcessingException JSON 변환 오류 발생 시 예외 발생
     */
    public void storeUserInRedis(String sessionId, UserWithTeamDTO user) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> data = new HashMap<>();

        // 비밀번호 및 Salt 정보 제거
        user.setSalt(null);
        user.setPasswd(null);

        // JSON 직렬화 후 Redis 저장
        data.put(sessionId, objectMapper.writeValueAsString(user));
        this.setHashOps(CommonConstant.USER_KEY, data, Duration.ofSeconds(CommonConstant.EXPIRY_DURATION_SECONDS));
    }
}
