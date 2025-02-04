package ssb.soccer.user.model;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class ChatMessage {
    private int id;
    private int chat_room_id;
    private int user_fk;
    private boolean read_or_not;
    private String message;
    private Date created_at;
}
