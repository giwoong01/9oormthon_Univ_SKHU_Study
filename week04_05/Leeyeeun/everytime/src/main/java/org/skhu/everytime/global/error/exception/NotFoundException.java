package org.skhu.everytime.global.error.exception;

// 특정 리소스나 엔티티를 찾을 수 없을 때 발생하는 예외 처리
public abstract class NotFoundException extends RuntimeException{
    public NotFoundException(String message) {

        super(message);
    }
}
