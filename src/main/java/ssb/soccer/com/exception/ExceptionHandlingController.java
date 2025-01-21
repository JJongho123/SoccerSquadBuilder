package ssb.soccer.com.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import ssb.soccer.com.api.dto.ApiResponse;

import java.nio.file.AccessDeniedException;
import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> exceptionError(HttpServletRequest req, Exception ex) {
        log.error("Request: {} | Exception: {}", req.getRequestURL(), ex);


        HttpStatus status = getHttpStatus(ex);
        ExceptionEnum exceptionEnum = ExceptionEnum.fromHttpStatus(status);

        ApiExceptionDto exceptionDto = ApiExceptionDto.builder()
                .errorCode(exceptionEnum.getCode())
                .errorMsg(ex.getMessage())
                .build();

        return ResponseEntity
                .status(status)
                .body(ApiResponse.errorResponse(exceptionDto, "EXCEPTION 발생"));
    }


    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse<?>> noResourceFoundExceptionError(HttpServletRequest req, NoResourceFoundException ex) {
        log.error("Request: {} | NoResourceFoundException: {}", req.getRequestURL(), ex);

        ApiExceptionDto exceptionDto = ApiExceptionDto.builder()
                .errorCode(ExceptionEnum.NO_RESOURCE_FOUND_EXCEPTION.getCode())
                .errorMsg(ex.getMessage())
                .build();

        return ResponseEntity
                .status(ex.getBody().getStatus())
                .body(ApiResponse.errorResponse(exceptionDto, "NoResourceFoundException 발생"));
    }

    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<ApiResponse<?>> customExceptionHandler(HttpServletRequest req, final CustomApiException ex) {

        log.error("Request: " + req.getRequestURL() + "| CustomApiException: " + ex);

        ApiExceptionDto exceptionDto = ApiExceptionDto.builder()
                .errorCode(ex.getError().getCode())
                .errorMsg(ex.getError().getMessage())
                .build();

        return ResponseEntity
                .status(ex.getError().getStatus())
                .body(ApiResponse.errorResponse(exceptionDto, ex.getError().getMessage()));
    }

    private HttpStatus getHttpStatus(Exception ex) {
        if (ex instanceof NoResourceFoundException || ex instanceof NoSuchElementException) {
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
