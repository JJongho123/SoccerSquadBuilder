package ssb.soccer.squad.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SquadRequestDto {
    private String squadType;
    private List<Integer> memberIds;
}
