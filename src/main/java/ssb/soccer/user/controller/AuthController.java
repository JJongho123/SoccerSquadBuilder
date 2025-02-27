package ssb.soccer.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "로그인", description = "로그인 프로세스 진행")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @PostMapping("/login")
    public ResponseEntity<CommonApiResponse<Boolean>> login(@RequestBody LoginRequestDto loginDto, HttpServletResponse response) throws JsonProcessingException {

        Cookie sessionCookie = authService.login(loginDto);
        boolean isSuccess = (sessionCookie != null);

        if (isSuccess) {
            response.addCookie(sessionCookie);
        }

        return ResponseEntity.ok(CommonApiResponse.successResponse(isSuccess));
    }

}
