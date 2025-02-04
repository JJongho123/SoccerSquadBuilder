package ssb.soccer.user.model;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class ChatRoom {
    private int id;
    private int noticeId;
    private int userFk;
    private Date createdAt;
}
