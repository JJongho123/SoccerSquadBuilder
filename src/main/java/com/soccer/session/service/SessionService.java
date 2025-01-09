package com.soccer.session.service;

import com.soccer.com.constant.CommonConstant;
import com.soccer.redis.service.RedisService;
import com.soccer.session.model.SessionData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class SessionService {

    private final RedisService redisService;

    public SessionData createSession(SessionData request) {

        String sessionId = UUID.randomUUID().toString();
        Map<Object, Object> sessionData = request.getSessionMapData();
        redisService.setHashOps(sessionId, sessionData, Duration.ofSeconds(CommonConstant.EXPIRY_DURATION_SECONDS));

        return SessionData.builder()
                .sessionId(sessionId)
                .sessionMapData(sessionData)
                .build();

    }

    public SessionData getSession(String sessionId) {

        Map<Object, Object> resultSessionData = redisService.getAllHashOps(sessionId);
        Long ttl = redisService.getTTL(sessionId);

        return SessionData.builder()
                .sessionId(sessionId)
                .sessionMapData(resultSessionData)
                .ttl(ttl)
                .build();
    }

    public boolean deleteSession(String sessionId) {
        return redisService.deleteKey(sessionId);
    }

    public boolean isSessionValid(String sessionId) {
        return redisService.exists(sessionId);
    }

}