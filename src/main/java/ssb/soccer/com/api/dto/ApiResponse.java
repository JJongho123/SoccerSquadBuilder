package ssb.soccer.com.api.dto;

import lombok.*;

@Getter
@NoArgsConstructor
public  class ApiResponse<T> {


    private static final String SUCCESS_STATUS = "success";
    private static final String FAIL_STATUS = "fail";
    private static final String ERROR_STATUS = "error";

    private String status;
    private T data;
    private String message;

    public ApiResponse(String status, T data, String message) {
            this.status = status;
            this.data = data;
            this.message = message;
    }
    public ApiResponse(String status, T data) {
        this.status = status;
        this.data = data;
    }

    public static <T> ApiResponse<T> successResponse(T data) {
        return new ApiResponse<>(SUCCESS_STATUS, data, null);
    }

    public static <T> ApiResponse<T> successResponse(T data, String message) {
        return new ApiResponse<>(SUCCESS_STATUS, data, message);
    }

    public static ApiResponse<?> successWithNoContent() {
        return new ApiResponse<>(SUCCESS_STATUS, null, null);
    }

    public static <T> ApiResponse<T> errorResponse(T data) {
        return new ApiResponse<>(ERROR_STATUS, data);
    }

    public static <T> ApiResponse<T> errorResponse(T data, String message) {
        return new ApiResponse<>(ERROR_STATUS, data, message);
    }
}
