package ssb.soccer.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    public int id;
    public String user_id;
    public String passwd;
    public String name;
    public String email;
    public String position;
    public int height;
    public int stamina;
}
