package ssb.soccer.team.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamDetailDto {
    private Integer id;
    private String teamName;
    private Integer teamMemberMaxCount;
    private String teamLevel;
    private String teamActivityArea;
    private String userId;
    private String name;
    private String teamMemberCount;
}
