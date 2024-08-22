package com.skhu.goormthon.global.template;

import com.skhu.goormthon.global.exception.code.ErrorCode;
import com.skhu.goormthon.global.exception.code.SuccessCode;
import lombok.*;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class ApiResTemplate<T> {

    private final boolean success;
    private final int status;
    private final String message;
    private T data;

    public static <T> ApiResTemplate<T> success(SuccessCode successCode, T data) {
        return ApiResTemplate.<T>builder()
                .success(true)
                .status(successCode.getHttpStatus().value())
                .message(successCode.getMessage())
                .data(data)
                .build();
    }

    public static <T> ApiResTemplate<T> error(ErrorCode errorCode) {
        return ApiResTemplate.<T>builder()
                .success(false)
                .status(errorCode.getHttpStatus().value())
                .message(errorCode.getMessage())
                .build();
    }
}
