package ssb.soccer.team.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamListDto {
    private Integer id;
    private String teamName;
    private Integer teamMemberMaxCount;
    private String teamLevel;
    private String teamActivityArea;
    private String userId;
}
