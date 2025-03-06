package ssb.soccer.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class LoginRequestDto {

    @Schema(description = "사용자 ID", example = "jjh33534", required = true)
    private String userId;

    @Schema(description = "비밀번호", example = "test1234@@", required = true)
    private String passwd;

}
