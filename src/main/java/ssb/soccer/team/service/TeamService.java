package ssb.soccer.team.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ssb.soccer.com.constant.CommonConstant;
import ssb.soccer.redis.service.RedisService;
import ssb.soccer.team.TeamEnum;
import ssb.soccer.team.dto.TeamListDto;
import ssb.soccer.team.dto.TeamRequestDto;
import ssb.soccer.team.mapper.TeamMapper;
import ssb.soccer.team.mapper.TeamMembershipMapper;
import ssb.soccer.team.model.Team;
import ssb.soccer.team.model.TeamMembership;
import ssb.soccer.user.model.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final RedisService redisService;
    private final TeamMapper teamMapper;
    private final TeamMembershipMapper teamMembershipMapper;

    public boolean createTeam(TeamRequestDto teamDto, String sessionId) throws JsonProcessingException {

        Team team = Team.builder()
                .teamName(teamDto.getTeamName())
                .teamLevel(teamDto.getTeamLevel())
                .teamActivityArea(teamDto.getTeamActivityArea())
                .teamMemberMaxCount(teamDto.getTeamMemberMaxCount())
                .id(null)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(redisService.getHashOps(CommonConstant.USER_KEY, sessionId), User.class);
        teamMapper.createTeam(team);

        TeamMembership teamMembership = TeamMembership.builder()
                .teamId(team.getId())
                .userFk(user.getId())
                .role(TeamEnum.TEAM_LEADER.getName())
                .id(null)
                .build();

        return teamMembershipMapper.addMember(teamMembership);
    }

    public List<TeamListDto> getTeamList(){
        return teamMapper.getTeamList();
    }
}
