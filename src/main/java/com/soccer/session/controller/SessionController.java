package com.soccer.session.controller;

import com.soccer.session.model.SessionData;
import com.soccer.session.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/session")
public class SessionController {

    private final SessionService sessionService;

    @PostMapping
    public ResponseEntity<SessionData> createSession(@RequestBody SessionData request) {
        SessionData session = sessionService.createSession(request);
        return ResponseEntity.ok(session);
    }

    @GetMapping("/{sessionId}")
    public ResponseEntity<SessionData> getSession(@PathVariable String sessionId) {
        SessionData session = sessionService.getSession(sessionId);
        if (session == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(session);
    }

    @DeleteMapping("/{sessionId}")
    public ResponseEntity<Boolean> deleteSession(@PathVariable String sessionId) {
        boolean result = sessionService.deleteSession(sessionId);
        return ResponseEntity.ok(result);
    }
}