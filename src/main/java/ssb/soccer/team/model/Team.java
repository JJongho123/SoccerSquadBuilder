package ssb.soccer.team.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Team {
    private int id;
    private String teamName;
    private int teamMemberMaxCount;
    private String teamLevel;
    private String teamActivityArea;
}
