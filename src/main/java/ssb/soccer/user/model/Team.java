package ssb.soccer.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Team {
    private int id;
    private String team_name;
    private int team_member_max_count;
    private String team_level;
    private String team_activity_area;
}
