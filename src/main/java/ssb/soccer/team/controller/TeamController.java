package ssb.soccer.team.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssb.soccer.com.api.dto.ApiResponse;
import ssb.soccer.com.util.CookieUtil;
import ssb.soccer.team.dto.TeamJoinRequestDto;
import ssb.soccer.team.dto.TeamRequestDto;
import ssb.soccer.team.service.TeamService;
import ssb.soccer.user.service.AuthService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/team")
public class TeamController {

    private final TeamService teamService;

    @PostMapping
    public void createTeam(@RequestBody TeamRequestDto teamDto, HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        String sessionId = CookieUtil.getCookieSessionId(request);
        teamService.createTeam(teamDto, sessionId);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getTeamList(){
        return ResponseEntity.ok(ApiResponse.successResponse(teamService.getTeamList()));
    }

    @GetMapping(value="{teamId}")
    public ResponseEntity<ApiResponse<?>> getTeamDetail(@PathVariable int teamId){
        return ResponseEntity.ok(ApiResponse.successResponse(teamService.getTeamDetail(teamId)));
    }

    @PutMapping("/join")
    public void joinTeam(@RequestBody TeamJoinRequestDto joinRequestDto){
        teamService.joinTeam(joinRequestDto);
    }

}
