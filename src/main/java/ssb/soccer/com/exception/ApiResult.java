package ssb.soccer.com.exception;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;


@Getter
@Setter
@Builder
public class ApiResult {

    private String resultMsg;
    private String status;
    private ApiExceptionDto exception;

}
