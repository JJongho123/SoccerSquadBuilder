package ssb.soccer.team.dto;

import lombok.Getter;

@Getter
public class TeamJoinRequestDto {
    private int userFk;
    private int teamId;
}
