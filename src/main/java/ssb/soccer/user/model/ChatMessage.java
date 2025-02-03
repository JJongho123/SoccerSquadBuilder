package ssb.soccer.user.model;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class ChatMessage {
    public int id;
    public int chat_room_id;
    public int user_fk;
    public boolean read_or_not;
    public String message;
    public Date created_at;
}
