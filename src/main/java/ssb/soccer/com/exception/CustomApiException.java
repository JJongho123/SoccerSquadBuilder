package ssb.soccer.com.exception;

import lombok.Getter;

@Getter
public class CustomApiException extends RuntimeException {

    private final ExceptionEnum error;

    public CustomApiException(ExceptionEnum e) {
        super(e.getMessage());
        this.error = e;
    }

    public CustomApiException(ExceptionEnum e, String errorMsg) {
        super(errorMsg);
        this.error = e;
    }
}
