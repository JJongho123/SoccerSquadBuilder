package ssb.soccer.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssb.soccer.com.api.dto.ApiResponse;
import ssb.soccer.com.constant.CommonConstant;
import ssb.soccer.redis.service.RedisService;
import ssb.soccer.user.dto.LoginRequestDto;
import ssb.soccer.user.dto.UserWithTeamDTO;
import ssb.soccer.user.model.User;
import ssb.soccer.user.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final RedisService redisService;

    @GetMapping("/")
    public ResponseEntity<ApiResponse<?>> getAllUsers() {
        return ResponseEntity.ok(ApiResponse.successResponse(userService.getAllUsers()));
    }

    @PostMapping
    public void createUser(@RequestBody User user) {
        userService.createUser(user);
    }

    @PostMapping("/check-duplicate-id")
    public ResponseEntity<ApiResponse<?>>  checkDuplicateId(@RequestParam String userId) {
        boolean isDuplicate = true;
        User user = userService.findById(userId);
        if(user == null) {
            isDuplicate = false;
        }
        return ResponseEntity.ok(ApiResponse.successResponse(isDuplicate));
    }


    @GetMapping("/info")
    public ResponseEntity<ApiResponse<?>> getUserInfo(HttpServletRequest request){
        UserWithTeamDTO responseDto = userService.getUserInfo(request);
        return ResponseEntity.ok(ApiResponse.successResponse(responseDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getUserById(@PathVariable int id) {
        return ResponseEntity.ok(ApiResponse.successResponse(userService.getUserById(id)));
    }

    @PutMapping()
    public void updateUserById(@RequestBody User user) {
        userService.updateUser(user);
    }

}