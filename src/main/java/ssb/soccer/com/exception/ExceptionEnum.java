package ssb.soccer.com.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

@Getter
@ToString
public enum ExceptionEnum {

    // System Exception
    RUNTIME_EXCEPTION(HttpStatus.BAD_REQUEST, "E0001"),                 // 400
    AUTHENTICATION_EXCEPTION(HttpStatus.UNAUTHORIZED, "E0002"),         // 401
    ACCESS_DENIED_EXCEPTION(HttpStatus.FORBIDDEN, "E0003"),             // 403
    NO_RESOURCE_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "E0004"),         // 404
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E0005"),   // 500

    // Custom Exception
    SECURITY(HttpStatus.UNAUTHORIZED, "CE0001", "로그인이 필요합니다"),
    PASSWD_POLICY_ERROR(HttpStatus.BAD_REQUEST, "CE0002", "비밀번호가 정책을 만족하지 않습니다."),
    UNAUTHORIZED_EXCEPTION(HttpStatus.UNAUTHORIZED, "CE0003", "사용자 인증이 유효하지 않습니다.");

    private final HttpStatus status;
    private final String code;
    private String message;

    ExceptionEnum(HttpStatus status, String code) {
        this.status = status;
        this.code = code;
    }

    ExceptionEnum(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public static ExceptionEnum fromHttpStatus(HttpStatus status) {
        return Arrays.stream(values())
                .filter(e -> e.getStatus() == status)
                .findFirst()
                .orElse(INTERNAL_SERVER_ERROR);
    }

}
