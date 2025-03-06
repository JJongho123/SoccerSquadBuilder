package ssb.soccer.team.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ssb.soccer.team.TeamEnum;

@Getter
@Setter
@Builder
public class TeamMembership {
    private Integer id;
    private int teamId;
    private int userFk;
    private String  role;
}
