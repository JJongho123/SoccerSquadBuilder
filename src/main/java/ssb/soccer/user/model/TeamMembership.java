package ssb.soccer.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamMembership {
    private int id;
    private int team_id;
    private int user_fk;
    private String role;
}
