package com.soccer.session.controller;

import com.soccer.com.constant.CommonConstant;
import com.soccer.session.model.SessionData;
import com.soccer.session.service.SessionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/session")
public class SessionController {

    private final SessionService sessionService;

    @PostMapping
    public ResponseEntity<SessionData> createSession(@RequestBody SessionData request, HttpServletResponse response) {
        SessionData session = sessionService.createSession(request);
        Cookie sessionCookie = new Cookie("sessionId", session.getSessionId());
        sessionCookie.setHttpOnly(true);
        sessionCookie.setPath("/");
        sessionCookie.setMaxAge(CommonConstant.EXPIRY_DURATION_SECONDS);

        response.addCookie(sessionCookie);
        return ResponseEntity.ok(session);
    }

    @GetMapping
    public ResponseEntity<SessionData> getSession(@RequestHeader("sessionId") String sessionId) {
        SessionData sessionData = sessionService.getSession(sessionId);
        return ResponseEntity.ok(sessionData);
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteSession(@RequestHeader("sessionId") String sessionId) {
        boolean result = sessionService.deleteSession(sessionId);
        return ResponseEntity.ok(result);
    }

//    @GetMapping("/check")
//    public ResponseEntity<String> respondWithSessionCheck(@RequestHeader(value = "session-id", required = false) String sessionId) {
//
//        // 세션 ID가 없으면 401 응답
//        if (sessionId == null || sessionId.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("세션 ID 없음!");
//        }
//
//        // 세션 ID가 잘못된 경우 401 응답
//        if (!sessionService.isSessionValid(sessionId)) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("세션 ID 잘못되었음!");
//        }
//
//        // 세션 ID가 유효하면 제대로 응답
//        return ResponseEntity.ok("세션 데이터 있음!");
//    }


}