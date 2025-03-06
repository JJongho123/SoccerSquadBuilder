package ssb.soccer.com.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import ssb.soccer.com.api.dto.CommonApiResponse;

import java.nio.file.AccessDeniedException;
import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlingController {

    /**
     * 전역 예외 처리 메서드
     *
     * 이 메서드는 모든 예외(Exception.class)를 처리하며,
     * 클라이언트에게 일관된 API 에러 응답을 반환합니다.
     *
     * @param req 예외가 발생한 HTTP 요청 정보 HttpServletRequest 객체
     * @param ex  처리 중 발생한 예외 정보
     * @return API 에러 응답을 표준 형식 ApiResponse 으로 감싸서 반환합니다.
     *         응답에는 HTTP 상태 코드와 에러 메시지가 포함됩니다.
     *
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonApiResponse<?>> exceptionError(HttpServletRequest req, Exception ex) {
        log.error("Request: {} | Exception: {}", req.getRequestURL(), ex);


        HttpStatus status = getHttpStatus(ex);
        ExceptionEnum exceptionEnum = ExceptionEnum.fromHttpStatus(status);

        ApiExceptionDto exceptionDto = ApiExceptionDto.builder()
                .errorCode(exceptionEnum.getCode())
                .errorMsg(ex.getMessage())
                .build();

        return ResponseEntity
                .status(status)
                .body(CommonApiResponse.errorResponse(exceptionDto, "EXCEPTION 발생"));
    }


    /**
     * 리소스 예외 처리 메서드
     *
     * 이 메서드는 NoResourceFoundException 를 처리합니다.
     *
     * @param req 예외가 발생한 HTTP 요청 정보 HttpServletRequest 객체
     * @param ex  처리 중 발생한 NoResourceFoundException 예외 정보
     * @return API 에러 응답을 표준 형식 ApiResponse 으로 감싸서 반환합니다.
     *         응답에는 HttpStatus.NOT_FOUND 404 코드와 에러 메시지가 포함됩니다.
     *
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<CommonApiResponse<?>> noResourceFoundExceptionError(HttpServletRequest req, NoResourceFoundException ex) {
        log.error("Request: {} | NoResourceFoundException: {}", req.getRequestURL(), ex);

        ApiExceptionDto exceptionDto = ApiExceptionDto.builder()
                .errorCode(ExceptionEnum.NO_RESOURCE_FOUND_EXCEPTION.getCode())
                .errorMsg(ex.getMessage())
                .build();

        return ResponseEntity
                .status(ex.getBody().getStatus())
                .body(CommonApiResponse.errorResponse(exceptionDto, "NoResourceFoundException 발생"));
    }

    /**
     * 사용자 커스텀 예외 처리 메서드
     *
     * 이 메서드는 CustomApiException 를 처리합니다.
     *
     * @param req 예외가 발생한 HTTP 요청 정보 HttpServletRequest 객체
     * @param ex  처리 중 발생한 CustomApiException 예외 정보
     * @return API 에러 응답을 표준 형식 ApiResponse 으로 감싸서 반환합니다.
     *         응답에는 사용자 커스텀 에러 코드와 사용자 커스텀 에러 메시지가 포함됩니다.
     *
     */
    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<CommonApiResponse<?>> customExceptionHandler(HttpServletRequest req, final CustomApiException ex) {

        log.error("Request: " + req.getRequestURL() + "| CustomApiException: " + ex);

        ApiExceptionDto exceptionDto = ApiExceptionDto.builder()
                .errorCode(ex.getError().getCode())
                .errorMsg(ex.getMessage())
                .build();

        return ResponseEntity
                .status(ex.getError().getStatus())
                .body(CommonApiResponse.errorResponse(exceptionDto, ex.getError().getMessage()));
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
