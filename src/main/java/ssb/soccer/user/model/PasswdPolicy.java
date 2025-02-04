package ssb.soccer.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswdPolicy {

    private int id;
    private String regex_pattern;
    private boolean is_active;
    private String pattern_name;
    private String description;

}
