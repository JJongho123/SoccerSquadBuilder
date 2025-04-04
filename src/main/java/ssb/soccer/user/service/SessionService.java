package ssb.soccer.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ssb.soccer.redis.service.RedisService;
import ssb.soccer.user.model.SessionData;

import java.util.Map;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class SessionService {

    private final RedisService redisService;

    public SessionData createSession(SessionData request) {

        String sessionId = UUID.randomUUID().toString();
        Map<Object, Object> sessionData = request.getSessionMapData();
//        redisService.setHashOps(sessionId, sessionData, Duration.ofSeconds(CommonConstant.EXPIRY_DURATION_SECONDS));

        return SessionData.builder()
                .sessionId(sessionId)
                .sessionMapData(sessionData)
                .build();

    }

//    public SessionData getSession(String sessionId) {
//
//        Map<Object, Object> resultSessionData = redisService.getAllHashOps(sessionId);
//        Long ttl = redisService.getTTL(sessionId);
//
//        return SessionData.builder()
//                .sessionId(sessionId)
//                .sessionMapData(resultSessionData)
//                .ttl(ttl)
//                .build();
//    }

    public boolean deleteSession(String sessionId) {
        return redisService.deleteKey(sessionId);
    }

    public boolean isSessionValid(String key, String sessionId) {
        return redisService.existsHashKey(key, sessionId);
    }

}