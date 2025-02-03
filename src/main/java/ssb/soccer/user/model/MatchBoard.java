package ssb.soccer.user.model;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class MatchBoard {
    public int id;
    public int user_fk;
    public String title;
    public String content;
    public Date created_at;
}
