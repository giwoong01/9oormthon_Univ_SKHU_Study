package org.goorm.everytime.global.common.exception.model;


import lombok.Getter;
import org.goorm.everytime.global.common.exception.ErrorCode;

@Getter
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getHttpStatus() {
        return errorCode.getHttpStatusCode();
    }
}
