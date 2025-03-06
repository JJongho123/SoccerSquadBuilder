package ssb.soccer.team.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ssb.soccer.team.model.Team;
import ssb.soccer.user.model.User;

import java.util.List;

@Getter
@Setter
@Builder
public class TeamListDto {

    private TeamDetailDto teamDetail;
    private List<User> userList;

}
