package org.skhu.everytime.global.error.exception;

// 유효하지 않은 데이터나 상태를 처리
public abstract class InvalidException extends RuntimeException{
    public InvalidException(String message) {

        super(message);
    }
}