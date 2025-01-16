package ssb.soccer.com.exception;

import lombok.Getter;

@Getter
public class CustomApiException extends RuntimeException {

    private ExceptionEnum error;

    public CustomApiException(ExceptionEnum e) {
        super(e.getMessage());
        this.error = e;
    }
}
