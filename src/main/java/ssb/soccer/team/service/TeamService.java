package ssb.soccer.team.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ssb.soccer.com.constant.CommonConstant;
import ssb.soccer.com.exception.CustomApiException;
import ssb.soccer.com.exception.ExceptionEnum;
import ssb.soccer.redis.service.RedisService;
import ssb.soccer.team.TeamEnum;
import ssb.soccer.team.dto.TeamDetailDto;
import ssb.soccer.team.dto.TeamJoinRequestDto;
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


    /**
     * 새로운 팀을 생성한다.
     * 팀 생성 후, 해당 팀의 팀장을 자동으로 등록한다.
     *
     * @param teamDto 팀 생성 요청 DTO
     * @param sessionId 사용자 세션 ID (Redis에서 사용자 정보 조회에 사용)
     * @throws CustomApiException 팀 생성 실패 시 발생
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void createTeam(TeamRequestDto teamDto, String sessionId) {

        Team team = Team.builder()
                .teamName(teamDto.getTeamName())
                .teamLevel(teamDto.getTeamLevel())
                .teamActivityArea(teamDto.getTeamActivityArea())
                .teamMemberMaxCount(teamDto.getTeamMemberMaxCount())
                .id(null)
                .build();

        UserWithTeamDTO user = redisService.getHashOpsAsObject(CommonConstant.USER_KEY, sessionId, UserWithTeamDTO.class);
        try {
            teamMapper.createTeam(team);
        } catch (Exception e) {
            throw new CustomApiException(ExceptionEnum.TEAM_CREATION_FAILED, e);
        }

        TeamMembership teamMembership = TeamMembership.builder()
                .teamId(team.getId())
                .userFk(user.getId())
                .role(TeamEnum.TEAM_LEADER.getName())
                .id(null)
                .build();

        try {
            teamMembershipMapper.createMemberShip(teamMembership);
        } catch (Exception e) {
            throw new CustomApiException(ExceptionEnum.TEAM_MEMBERSHIP_CREATION_FAILED, e);
        }

    }

    /**
     * 전체 팀 목록을 조회한다.
     *
     * @return 팀 상세 정보 목록
     */
    public List<TeamDetailDto> getTeamList() {
        return teamMapper.getTeamList();
    }

    /**
     * 특정 팀의 상세 정보를 조회한다.
     *
     * @param teamId 조회할 팀의 ID
     * @return 해당 팀의 상세 정보
     */
    public TeamListDto getTeamDetail(int teamId) {

        TeamListDto teamListDto = TeamListDto.builder()
                .teamDetail(teamMapper.getTeamDetail(teamId))
                .userList(userMapper.findUserList(teamId))
                .build();

        return teamListDto;
    }


    /**
     * 사용자를 특정 팀에 가입시킨다.
     *
     * @param joinRequestDto 팀 가입 요청 DTO
     * @throws CustomApiException 팀 가입 실패 시 발생
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void joinTeam(TeamJoinRequestDto joinRequestDto){

        int userFk = joinRequestDto.getUserFk();
        int teamId = joinRequestDto.getTeamId();

        TeamMembership teamMembership = TeamMembership.builder()
                .teamId(teamId)
                .userFk(userFk)
                .role(TeamEnum.TEAM_MEMBER.getName())
                .id(null)
                .build();

        try {
            teamMembershipMapper.createMemberShip(teamMembership);
        } catch (Exception e) {
            throw new CustomApiException(ExceptionEnum.TEAM_MEMBERSHIP_CREATION_FAILED, e);
        }
    }

}
