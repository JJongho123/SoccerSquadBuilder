package ssb.soccer.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssb.soccer.com.api.dto.ApiResponse;
import ssb.soccer.user.dto.LoginDto;
import ssb.soccer.user.service.AuthService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@RequestBody LoginDto loginDto, HttpServletResponse response) throws JsonProcessingException {

        Cookie sessionCookie = authService.login(loginDto);
        boolean isSuccess = (sessionCookie != null);

        if (isSuccess) {
            response.addCookie(sessionCookie);
        }

        return ResponseEntity.ok(ApiResponse.successResponse(isSuccess));
    }
}
