package ssb.soccer.user.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserWithTeamDTO {

    //User 데이터
    private int id;
    private String userId;
    private String passwd;
    private String name;
    private String salt;
    private String email;
    private String position;
    private int height;
    private int stamina;
    private int age;
    private String mainFoot;

    // Team 데이터
    private String teamId;
    private String role;
}

