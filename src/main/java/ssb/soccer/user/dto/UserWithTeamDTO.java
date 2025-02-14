package ssb.soccer.user.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserWithTeamDTO {
    private int id;
    private String userId;
    private String passwd;
    private String name;
    private String teamId;
    private String role;
    private String salt;
}
