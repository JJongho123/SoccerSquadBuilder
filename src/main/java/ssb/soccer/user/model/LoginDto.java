package ssb.soccer.user.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class LoginDto {

    private String userId;
    private String passwd;

}
