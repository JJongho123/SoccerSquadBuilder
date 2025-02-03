package ssb.soccer.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamMembership {
    public int id;
    public int team_id;
    public int user_fk;
    public String role;
}
