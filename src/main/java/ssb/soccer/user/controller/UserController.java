package ssb.soccer.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssb.soccer.com.api.dto.CommonApiResponse;
import ssb.soccer.user.dto.UserWithTeamDTO;
import ssb.soccer.user.model.User;
import ssb.soccer.user.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "선수 목록 조회",
            description = "모든 선수 목록을 조회합니다.",
            tags = {"User API"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "500", description = "서버 에러")
    })
    @GetMapping("/")
    public ResponseEntity<CommonApiResponse<?>> getAllUsers() {
        return ResponseEntity.ok(CommonApiResponse.successResponse(userService.getAllUsers()));
    }

    @Operation(summary = "선수 등록", description = "새로운 선수를 등록합니다.")
    @PostMapping
    public void createUser(@RequestBody User user) {
        userService.createUser(user);
    }

    @PostMapping("/check-duplicate-id")
    public ResponseEntity<CommonApiResponse<?>>  checkDuplicateId(@RequestParam String userId) {
        boolean isDuplicate = true;
        User user = userService.findById(userId);
        if(user == null) {
            isDuplicate = false;
        }
        return ResponseEntity.ok(CommonApiResponse.successResponse(isDuplicate));
    }


    @GetMapping("/info")
    public ResponseEntity<CommonApiResponse<?>> getUserInfo(HttpServletRequest request){
        UserWithTeamDTO responseDto = userService.getUserInfo(request);
        return ResponseEntity.ok(CommonApiResponse.successResponse(responseDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonApiResponse<?>> getUserById(@PathVariable int id) {
        return ResponseEntity.ok(CommonApiResponse.successResponse(userService.getUserById(id)));
    }

    @PutMapping()
    public void updateUserById(@RequestBody User user) {
        userService.updateUser(user);
    }

}