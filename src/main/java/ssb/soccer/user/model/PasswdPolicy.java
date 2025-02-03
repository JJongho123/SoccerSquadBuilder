package ssb.soccer.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswdPolicy {

    public int id;
    public String regex_pattern;
    public boolean is_active;
    public String pattern_name;
    public String description;

}
