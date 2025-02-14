package ssb.soccer.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import jakarta.servlet.http.Cookie;

@Setter
@Getter
@Builder
public class LoginResponseDto {
    private Cookie cookie;
    private boolean hasTeam;
    private String role;
}
