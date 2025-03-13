package ssb.soccer.history.dto;

import lombok.Setter;

import java.util.Date;

@Setter
public class HistoryRequestDto {
    private int teamId;
    private String gptResponseText;
    private String title;
    private String squadType;
    private Date createdAt;
}
