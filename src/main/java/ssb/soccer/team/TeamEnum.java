package ssb.soccer.team;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum TeamEnum {
    TEAM_LEADER("팀장");

    private final String name;

    TeamEnum(String name) {
        this.name = name;
    }
}
