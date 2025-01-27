package ssb.soccer.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswdPolicy {

    private String pattern_name;
    private String regex_pattern;
    private Boolean is_active;
    private String description;

}
