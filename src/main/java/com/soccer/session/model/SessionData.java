package com.soccer.session.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
public class SessionData {
    private String sessionId;
    private Long ttl;
    private Map<Object, Object> sessionMapData;
}