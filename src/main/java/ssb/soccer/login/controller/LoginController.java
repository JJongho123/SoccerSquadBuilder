package ssb.soccer.login.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssb.soccer.com.constant.CommonConstant;
import ssb.soccer.identity.session.model.SessionData;
import ssb.soccer.identity.session.service.SessionService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/login")
public class LoginController {

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

}
