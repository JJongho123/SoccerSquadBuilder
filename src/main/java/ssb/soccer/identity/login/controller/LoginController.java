package ssb.soccer.identity.login.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssb.soccer.identity.session.service.SessionService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/login")
public class LoginController {

    private final SessionService sessionService;

//    @PostMapping
//    public ResponseEntity<SessionData> createSession(@RequestBody LoginRequestDTO request, HttpServletResponse response) {
//        if(sessionService.isSessionValid(request.getUserId())){
//            sessionService.getSession()
//            SessionData session = sessionService.createSession(request);
//            Cookie sessionCookie = new Cookie("sessionId", session.getSessionId());
//            sessionCookie.setHttpOnly(true);
//            sessionCookie.setPath("/");
//            sessionCookie.setMaxAge(CommonConstant.EXPIRY_DURATION_SECONDS);
//            response.addCookie(sessionCookie);
//        }
//        SessionData session = sessionService.createSession(request);
//        Cookie sessionCookie = new Cookie("sessionId", session.getSessionId());
//        sessionCookie.setHttpOnly(true);
//        sessionCookie.setPath("/");
//        sessionCookie.setMaxAge(CommonConstant.EXPIRY_DURATION_SECONDS);
//        response.addCookie(sessionCookie);
//
//        return ResponseEntity.ok(session);
//    }

}
