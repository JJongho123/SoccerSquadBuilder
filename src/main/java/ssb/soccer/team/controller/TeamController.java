package ssb.soccer.team.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssb.soccer.com.api.dto.ApiResponse;
import ssb.soccer.team.dto.TeamRequestDto;
import ssb.soccer.team.service.TeamService;
import ssb.soccer.user.service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/team")
public class TeamController {

    private final AuthService authService;
    private final TeamService teamService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createTeam(@RequestBody TeamRequestDto teamDto, HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        String sessionId = authService.getCookieSessionId(request);
        return ResponseEntity.ok(ApiResponse.successResponse(teamService.createTeam(teamDto, sessionId)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getTeamList(){
        System.out.println(teamService.getTeamList());
        return ResponseEntity.ok(ApiResponse.successResponse(teamService.getTeamList()));
    }
}
