package org.goorm.everytime.global.common.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.goorm.everytime.global.common.exception.ErrorCode;
import org.goorm.everytime.global.common.exception.SuccessCode;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {
    private final int statusCode;
    private final String message;
    private T data;

    public static BaseResponse success(SuccessCode successCode) {
        return new BaseResponse<>(successCode.getHttpStatusCode(), successCode.getMessage());
    }

    public static <T> BaseResponse<T> success(SuccessCode successCode, T data) {
        return new BaseResponse<T>(successCode.getHttpStatusCode(), successCode.getMessage(), data);
    }

    public static BaseResponse error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode.getHttpStatusCode(), errorCode.getMessage());
    }

    public static BaseResponse error(ErrorCode errorCode, String message) {
        return new BaseResponse<>(errorCode.getHttpStatusCode(), message);
    }
}
