package ssb.soccer.team.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Team {
    private Integer id;
    private String teamName;
    private int teamMemberMaxCount;
    private String teamLevel;
    private String teamActivityArea;
}
