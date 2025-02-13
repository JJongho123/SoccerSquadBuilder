package ssb.soccer.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private int id;
    private String userId;
    private String passwd;
    private String name;
    private String email;
    private String position;
    private int height;
    private int stamina;
    private String salt;
}
