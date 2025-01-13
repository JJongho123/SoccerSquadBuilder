package ssb.soccer.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssb.soccer.user.auth.PasswdVaildationService;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/validation")
public class PasswdVaildationController {

    private final PasswdVaildationService passwdVaildationService;

    @GetMapping("/password-policies")
    public ResponseEntity<HashMap<String, Object>> initPasswdPolicyDatas() {
        HashMap<String, Object> data = passwdVaildationService.initPasswdPolicyDatas();
        return ResponseEntity.ok(data);
    }
}
