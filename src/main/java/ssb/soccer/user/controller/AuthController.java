package ssb.soccer.user.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssb.soccer.com.api.dto.ApiResponse;
import ssb.soccer.com.constant.CommonConstant;
import ssb.soccer.user.model.LoginDto;
import ssb.soccer.user.auth.AuthService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@RequestBody LoginDto loginDto, HttpServletResponse response) {

        boolean result = authService.login(loginDto);
        if(result){
            response.addCookie(setCookie(loginDto.getUserId()));
        }
        return ResponseEntity.ok(ApiResponse.successResponse(result));

    }

    private Cookie setCookie(String sessionId){

        Cookie sessionCookie = new Cookie("sessionId", sessionId);
        sessionCookie.setHttpOnly(true);
        sessionCookie.setPath("/");
        sessionCookie.setMaxAge(CommonConstant.EXPIRY_DURATION_SECONDS);
        return sessionCookie;

    }
}
