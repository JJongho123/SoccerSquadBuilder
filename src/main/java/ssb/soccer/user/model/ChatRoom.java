package ssb.soccer.user.model;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class ChatRoom {
    public int id;
    public int notice_id;
    public int user_fk;
    public Date created_at;
}
