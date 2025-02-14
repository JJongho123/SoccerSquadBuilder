package ssb.soccer.team.mapper;

import ssb.soccer.team.dto.TeamListDto;
import ssb.soccer.team.model.Team;

import java.util.List;

public interface TeamMapper {
    boolean createTeam(Team team);

    List<TeamListDto> getTeamList();
}
