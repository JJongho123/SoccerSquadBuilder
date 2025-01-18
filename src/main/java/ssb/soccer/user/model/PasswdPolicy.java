package ssb.soccer.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswdPolicy {

    private String patternName;
    private String regexPattern;
    private Boolean isActive;
    private String description;

}
