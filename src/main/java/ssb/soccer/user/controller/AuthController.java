package ssb.soccer.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssb.soccer.com.api.dto.CommonApiResponse;
import ssb.soccer.com.constant.CommonConstant;
import ssb.soccer.com.util.CookieUtil;
import ssb.soccer.redis.service.RedisService;
import ssb.soccer.user.dto.LoginRequestDto;
import ssb.soccer.user.service.AuthService;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final RedisService redisService;

    @Operation(summary = "로그인", description = "로그인 프로세스 진행")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @PostMapping("/login")
    public ResponseEntity<CommonApiResponse<Boolean>> login(@RequestBody LoginRequestDto loginDto, HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {

        Cookie sessionCookie = authService.login(loginDto, request);
        boolean isSuccess = (sessionCookie != null);

        if (isSuccess) {
            response.addCookie(sessionCookie);
        }

        return ResponseEntity.ok(CommonApiResponse.successResponse(isSuccess));
    }

    @Operation(summary = "로그아웃", description = "로그아웃 후 클라이언트에서 로그인 페이지로 이동")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "로그아웃 성공 (리다이렉트는 클라이언트에서 처리)"),
            @ApiResponse(responseCode = "401", description = "세션이 유효하지 않음")
    })
    @PutMapping("/logout")
    public ResponseEntity<CommonApiResponse<?>> logout(HttpServletRequest request) {
        String sessionId = CookieUtil.getCookieSessionId(request);
        redisService.deleteHashOps(CommonConstant.USER_KEY, sessionId);

        // 204 No Content 반환 (클라이언트가 리다이렉트 처리)
        return ResponseEntity.noContent().build();
    }

}
