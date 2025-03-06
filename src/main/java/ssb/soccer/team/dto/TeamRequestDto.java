package ssb.soccer.team.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamRequestDto {
    private String teamName;
    private int teamMemberMaxCount;
    private String teamLevel;
    private String teamActivityArea;
}
