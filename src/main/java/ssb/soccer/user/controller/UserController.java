package ssb.soccer.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Operation(summary = "모든 사용자 목록 조회", description = "모든 사용자 정보를 목록 형태로 반환합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @GetMapping()
    public ResponseEntity<CommonApiResponse<List<User>>> getAllUsers() {
        return ResponseEntity.ok(CommonApiResponse.successResponse(userService.getAllUsers()));
    }

    @Operation(summary = "사용자 등록", description = "새로운 사용자를 등록합니다.")
    @PostMapping
    public void createUser(@RequestBody User user) {
        userService.createUser(user);
    }

    @Operation(summary = "ID 중복 체크", description = "ID 중복 체크를 합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @PostMapping("/check-duplicate-id")
    public ResponseEntity<CommonApiResponse<Boolean>>  checkDuplicateId(
            @Parameter(description = "사용자 ID", required = true, example = "jjh33534")
            @RequestParam String userId
    ) {
        boolean isDuplicate = true;
        User user = userService.findById(userId);
        if(user == null) {
            isDuplicate = false;
        }
        return ResponseEntity.ok(CommonApiResponse.successResponse(isDuplicate));
    }

    @Operation(summary = "현재 사용자 정보 조회", description = "현재 세션을 기반으로 사용자 정보를 조회한다.(Look-aside 캐싱 적용)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @GetMapping("/info")
    public ResponseEntity<CommonApiResponse<UserWithTeamDTO>> getUserInfo(HttpServletRequest request) throws JsonProcessingException {
        UserWithTeamDTO responseDto = userService.getUserInfo(request, null);
        return ResponseEntity.ok(CommonApiResponse.successResponse(responseDto));
    }

    @Operation(summary = "해당 ID의 사용자 정보 조회", description = "주어진 ID에 해당하는 사용자의 정보를 반환합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CommonApiResponse<User>> getUserById(@PathVariable int id) {
        return ResponseEntity.ok(CommonApiResponse.successResponse(userService.getUserById(id)));
    }

    @Operation(summary = "사용자 정보 수정", description = "사용자 정보를 수정 합니다.")
    @PutMapping()
    public void updateUserById(@RequestBody User user) {
        userService.updateUser(user);
    }

}