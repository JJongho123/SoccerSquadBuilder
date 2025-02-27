package ssb.soccer.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssb.soccer.com.api.dto.CommonApiResponse;
import ssb.soccer.user.dto.LoginRequestDto;
import ssb.soccer.user.service.AuthService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<CommonApiResponse<?>> login(@RequestBody LoginRequestDto loginDto, HttpServletResponse response) throws JsonProcessingException {

        Cookie sessionCookie = authService.login(loginDto);
        boolean isSuccess = (sessionCookie != null);

        if (isSuccess) {
            response.addCookie(sessionCookie);
        }

        return ResponseEntity.ok(CommonApiResponse.successResponse(isSuccess));
    }

}
