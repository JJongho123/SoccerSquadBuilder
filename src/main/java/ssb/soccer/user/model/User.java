package ssb.soccer.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private long id;
    private String name;
    private String email;
    private String passwd;
    private String user_id;
    private String team_name;
    private String team_permission;

}
