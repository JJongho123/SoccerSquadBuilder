package ssb.soccer.com.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.nio.file.AccessDeniedException;
import java.sql.SQLException;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResult> exceptionError(HttpServletRequest req, Exception ex) {
        log.error("Request: " + req.getRequestURL() + "| Exception: " + ex);

        HttpStatus status = getHttpStatus(ex);

        ApiExceptionDto exceptionDto = ApiExceptionDto.builder()
                .errorCode(ExceptionEnum.INTERNAL_SERVER_ERROR.getCode())
                .errorMsg(ex.getMessage())
                .build();

        ApiResult apiResult = ApiResult.builder()
                .resultMsg("EXCEPTION 발생")
                .exception(exceptionDto)
                .status("error")
                .build();

        return ResponseEntity
                .status(status)
                .body(apiResult);
    }


    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResult> noResourceFoundExceptionError(HttpServletRequest req, NoResourceFoundException ex) {
        log.error("Request: " + req.getRequestURL() + "| Exception: " + ex);

        ApiExceptionDto exceptionDto = ApiExceptionDto.builder()
                .errorCode(ExceptionEnum.NO_RESOURCE_FOUND_EXCEPTION.getCode())
                .errorMsg(ex.getMessage())
                .build();

        ApiResult apiResult = ApiResult.builder()
                .resultMsg("NoResourceFoundException 발생")
                .exception(exceptionDto)
                .status("error")
                .build();

        return ResponseEntity
                .status(ex.getBody().getStatus())
                .body(apiResult);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResult> exceptionHandler(HttpServletRequest req, final ApiException ex) {

        log.error("Request: " + req.getRequestURL() + "| Exception: " + ex);

        ApiExceptionDto apiExceptionDto = ApiExceptionDto.builder()
                .errorCode(ex.getError().getCode())
                .errorMsg(ex.getError().getMessage())
                .build();

        return ResponseEntity
                .status(ex.getError().getStatus())
                .body(ApiResult.builder()
                        .status("error")
                        .resultMsg("")
                        .exception(apiExceptionDto)
                        .build());
    }

    private HttpStatus getHttpStatus(Exception ex) {
        if (ex instanceof NoResourceFoundException) {
            return HttpStatus.NOT_FOUND; // 404
        } else if (ex instanceof IllegalArgumentException) {
            return HttpStatus.BAD_REQUEST; // 400
        } else if (ex instanceof AccessDeniedException) {
            return HttpStatus.FORBIDDEN; // 403
        } else {
            return HttpStatus.INTERNAL_SERVER_ERROR; // 500
        }
    }

}
