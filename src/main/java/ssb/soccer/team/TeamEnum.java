package ssb.soccer.team;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum TeamEnum {

    TEAM_LEADER("팀장"),
    TEAM_COACH("코치"),
    TEAM_MEMBER("팀원");

    private final String name;

    TeamEnum(String name) {
        this.name = name;
    }
}
