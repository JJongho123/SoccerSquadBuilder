package ssb.soccer.team.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssb.soccer.com.api.dto.CommonApiResponse;
import ssb.soccer.com.util.CookieUtil;
import ssb.soccer.team.dto.TeamDetailDto;
import ssb.soccer.team.dto.TeamJoinRequestDto;
import ssb.soccer.team.dto.TeamListDto;
import ssb.soccer.team.dto.TeamRequestDto;
import ssb.soccer.team.service.TeamService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/team")
public class TeamController {

    private final TeamService teamService;

    @Operation(summary = "로그인", description = "로그인 프로세스 입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @PostMapping
    public void createTeam(@RequestBody TeamRequestDto teamDto, HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        String sessionId = CookieUtil.getCookieSessionId(request);
        teamService.createTeam(teamDto, sessionId);
    }

    @Operation(summary = "팀 목록 조회", description = "팀 목록 조회 입니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @GetMapping("/list")
    public ResponseEntity<CommonApiResponse<List<TeamDetailDto>>> getTeamList(){
        return ResponseEntity.ok(CommonApiResponse.successResponse(teamService.getTeamList()));
    }

    @Operation(summary = "해당 team id 팀 조회", description = "주어진 ID에 해당하는 팀 정보를 반환합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @GetMapping(value="{teamId}")
    public ResponseEntity<CommonApiResponse<TeamListDto>> getTeamDetail(@PathVariable int teamId){
        return ResponseEntity.ok(CommonApiResponse.successResponse(teamService.getTeamDetail(teamId)));
    }

    @Operation(summary = "팀 가입", description = "팀 가입 프로세스를 진행합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @PutMapping("/join")
    public void joinTeam(@RequestBody TeamJoinRequestDto joinRequestDto){
        teamService.joinTeam(joinRequestDto);
    }

}
