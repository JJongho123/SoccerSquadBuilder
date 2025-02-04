package ssb.soccer.user.model;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class MatchBoard {
    private int id;
    private int user_fk;
    private String title;
    private String content;
    private Date created_at;
}
