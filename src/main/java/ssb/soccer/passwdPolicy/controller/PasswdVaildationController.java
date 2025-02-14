package ssb.soccer.passwdPolicy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssb.soccer.com.api.dto.ApiResponse;
import ssb.soccer.passwdPolicy.service.PasswdVaildationService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/validation")
public class PasswdVaildationController {

    private final PasswdVaildationService passwdVaildationService;

    @GetMapping("/password-policies")
    public ResponseEntity<ApiResponse<?>> initPasswdPolicyDatas() {
        Map<String, Object> data = passwdVaildationService.initPasswdPolicyDatas();
        return ResponseEntity.ok(ApiResponse.successResponse(data));
    }
}
