package ssb.soccer.com.api.dto;

import lombok.*;

@Getter
@NoArgsConstructor
public class CommonApiResponse<T> {


    private static final String SUCCESS_STATUS = "success";
    private static final String FAIL_STATUS = "fail";
    private static final String ERROR_STATUS = "error";

    private String status;
    private T data;
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
