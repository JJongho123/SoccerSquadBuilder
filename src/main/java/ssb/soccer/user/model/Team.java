package ssb.soccer.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Team {
    public int id;
    public String team_name;
    public int team_member_max_count;
    public String team_level;
    public String team_activity_area;
}
