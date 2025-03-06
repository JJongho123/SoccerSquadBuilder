package ssb.soccer.passwdPolicy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssb.soccer.com.api.dto.CommonApiResponse;
import ssb.soccer.passwdPolicy.dto.PasswordPolicyResponseDTO;
import ssb.soccer.passwdPolicy.service.PasswdVaildationService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/validation")
public class PasswdVaildationController {

    private final PasswdVaildationService passwdVaildationService;

    @Operation(summary = "비밀번호 정책 조회", description = "비밀번호 정책 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @GetMapping("/password-policies")
    public ResponseEntity<CommonApiResponse<PasswordPolicyResponseDTO>> initPasswdPolicyDatas() {
        PasswordPolicyResponseDTO data = passwdVaildationService.initPasswdPolicyDatas();
        return ResponseEntity.ok(CommonApiResponse.successResponse(data));
    }
}
