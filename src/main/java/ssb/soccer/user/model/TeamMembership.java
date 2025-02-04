package ssb.soccer.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamMembership {
    private int id;
    private int teamId;
    private int userFk;
    private String role;
}
