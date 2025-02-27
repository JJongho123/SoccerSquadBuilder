package ssb.soccer.com.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@NoArgsConstructor
@Schema(description = "공통 API 응답 모델")
public class CommonApiResponse<T> {


    private static final String SUCCESS_STATUS = "success";
    private static final String FAIL_STATUS = "fail";
    private static final String ERROR_STATUS = "error";

    @Schema(description = "상태", example = "success")
    private String status;

    @Schema(description = "데이터")
    private T data;

    @Schema(description = "메시지", example = "응답 성공")
    private String message;

    public CommonApiResponse(String status, T data, String message) {
            this.status = status;
            this.data = data;
            this.message = message;
    }
    public CommonApiResponse(String status, T data) {
        this.status = status;
        this.data = data;
    }

    public static <T> CommonApiResponse<T> successResponse(T data) {
        return new CommonApiResponse<>(SUCCESS_STATUS, data, null);
    }

    public static <T> CommonApiResponse<T> successResponse(T data, String message) {
        return new CommonApiResponse<>(SUCCESS_STATUS, data, message);
    }

    public static CommonApiResponse<?> successWithNoContent() {
        return new CommonApiResponse<>(SUCCESS_STATUS, null, null);
    }

    public static <T> CommonApiResponse<T> errorResponse(T data) {
        return new CommonApiResponse<>(ERROR_STATUS, data);
    }

    public static <T> CommonApiResponse<T> errorResponse(T data, String message) {
        return new CommonApiResponse<>(ERROR_STATUS, data, message);
    }
}
