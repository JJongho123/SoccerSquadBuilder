package ssb.soccer.user.model;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class MatchBoard {
    private int id;
    private int userFk;
    private String title;
    private String content;
    private Date createdAt;
}
