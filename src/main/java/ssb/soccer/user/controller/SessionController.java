package ssb.soccer.user.controller;

import ssb.soccer.com.constant.CommonConstant;
import ssb.soccer.user.model.SessionData;
import ssb.soccer.user.auth.SessionService;
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

}