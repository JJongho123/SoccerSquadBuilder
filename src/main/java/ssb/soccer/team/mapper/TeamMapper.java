package ssb.soccer.team.mapper;

import ssb.soccer.team.dto.TeamDetailDto;
import ssb.soccer.team.model.Team;

import java.util.List;

public interface TeamMapper {
    void createTeam(Team team);

    List<TeamDetailDto> getTeamList();

    TeamDetailDto getTeamDetail(int teamId);
}
