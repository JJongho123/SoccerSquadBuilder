package ssb.soccer.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssb.soccer.com.api.dto.ApiResponse;
import ssb.soccer.user.model.User;
import ssb.soccer.user.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity<ApiResponse<?>> getAllUsers() {
        return ResponseEntity.ok(ApiResponse.successResponse(userService.getAllUsers()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>>  createUser(@RequestBody User user) {
        Boolean result =  userService.createUser(user);
        return ResponseEntity.ok(ApiResponse.successResponse(result));
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

//    @GetMapping("/{id}")
//    public User getUserById(@PathVariable String id) {
//        return userService.getUserById(id);
//    }

}