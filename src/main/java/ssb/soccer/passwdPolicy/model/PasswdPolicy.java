package ssb.soccer.passwdPolicy.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswdPolicy {

    private int id;
    private String regexPattern;
    private boolean isActive;
    private String patternName;
    private String description;

}
