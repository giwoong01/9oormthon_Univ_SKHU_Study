package org.skhu.everytime.global.error.exception;

// 유효하지 않은 요청 예외 처리
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
