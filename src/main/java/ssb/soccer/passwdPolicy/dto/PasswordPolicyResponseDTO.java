package ssb.soccer.passwdPolicy.dto;


import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(description = "비밀번호 정책 응답 모델")
public class PasswordPolicyResponseDTO {

    @ArraySchema(schema = @Schema(description = "비밀번호 정책 설명 목록", example = "숫자와 문자가 포함되어야 합니다."))
    private List<String> policies;

    @Schema(description = "비밀번호 정규 표현식", example = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=]).{8,16}$")
    private String regexPattern;
}