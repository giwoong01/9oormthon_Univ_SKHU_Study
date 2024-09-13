package com.goormthon.everytime.global.template;

import com.goormthon.everytime.global.exception.code.ErrorCode;
import com.goormthon.everytime.global.exception.code.SuccessCode;
import lombok.*;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class ApiResTemplate<T> {

    private final int status;
    private final String message;
    private T data;

    public static <T> ApiResTemplate<T> success(SuccessCode successCode, T data) {
        return ApiResTemplate.<T>builder()
                .status(successCode.getHttpStatus().value())
                .message(successCode.getMessage())
                .data(data)
                .build();
    }

    public static <T> ApiResTemplate<T> error(ErrorCode errorCode) {
        return ApiResTemplate.<T>builder()
                .status(errorCode.getHttpStatus().value())
                .message(errorCode.getMessage())
                .build();
    }
}
