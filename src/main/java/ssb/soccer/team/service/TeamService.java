package ssb.soccer.team.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ssb.soccer.com.constant.CommonConstant;
import ssb.soccer.com.exception.CustomApiException;
import ssb.soccer.com.exception.ExceptionEnum;
import ssb.soccer.redis.service.RedisService;
import ssb.soccer.team.TeamEnum;
import ssb.soccer.team.dto.TeamDetailDto;
import ssb.soccer.team.dto.TeamListDto;
import ssb.soccer.team.dto.TeamRequestDto;
import ssb.soccer.team.mapper.TeamMapper;
import ssb.soccer.team.mapper.TeamMembershipMapper;
import ssb.soccer.team.model.Team;
import ssb.soccer.team.model.TeamMembership;
import ssb.soccer.user.dto.UserWithTeamDTO;
import ssb.soccer.user.mapper.UserMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final RedisService redisService;
    private final TeamMapper teamMapper;
    private final UserMapper userMapper;
    private final TeamMembershipMapper teamMembershipMapper;

    public void createTeam(TeamRequestDto teamDto, String sessionId) throws JsonProcessingException {

        Team team = Team.builder()
                .teamName(teamDto.getTeamName())
                .teamLevel(teamDto.getTeamLevel())
                .teamActivityArea(teamDto.getTeamActivityArea())
                .teamMemberMaxCount(teamDto.getTeamMemberMaxCount())
                .id(null)
                .build();

        UserWithTeamDTO user = redisService.getHashOpsAsObject(CommonConstant.USER_KEY, sessionId, UserWithTeamDTO.class);
        boolean isCreatedTeam = teamMapper.createTeam(team);
        if (isCreatedTeam) {
            throw new CustomApiException(ExceptionEnum.TEAM_CREATION_FAILED);
        }

        TeamMembership teamMembership = TeamMembership.builder()
                .teamId(team.getId())
                .userFk(user.getId())
                .role(TeamEnum.TEAM_LEADER.getName())
                .id(null)
                .build();

        boolean isCreatedMembership = teamMembershipMapper.createMemberShip(teamMembership);
        if (isCreatedMembership) {
            throw new CustomApiException(ExceptionEnum.TEAM_MEMBERSHIP_CREATION_FAILED);
        }

    }

    public List<TeamDetailDto> getTeamList(){
        return teamMapper.getTeamList();
    }


    public TeamListDto getTeamDetail(int teamId) {

        TeamListDto teamListDto = TeamListDto.builder()
                .teamDetail(teamMapper.getTeamDetail(teamId))
                .userList(userMapper.findUserListWithTeam(teamId))
                .build();

        return teamListDto;
    }
}
