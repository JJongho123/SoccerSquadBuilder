package ssb.soccer.history.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class History {
    private int id;
    private int teamId;
    private String gptResponseText;
    private String title;
    private String squadType;
    private Date createdAt;
}
