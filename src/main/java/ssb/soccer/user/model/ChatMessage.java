package ssb.soccer.user.model;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class ChatMessage {
    private int id;
    private int chatRoomId;
    private int userFk;
    private boolean readOrNot;
    private String message;
    private Date createdAt;
}
