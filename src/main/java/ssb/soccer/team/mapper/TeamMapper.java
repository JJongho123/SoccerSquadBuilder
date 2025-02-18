package ssb.soccer.team.mapper;

import ssb.soccer.team.dto.TeamDetailDto;
import ssb.soccer.team.model.Team;

import java.util.List;

public interface TeamMapper {
    boolean createTeam(Team team);

    List<TeamDetailDto> getTeamList();

    TeamDetailDto getTeamDetail(int teamId);
}
