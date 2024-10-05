package com.example.everytime.auth.jwt.exception;

public class InvalidMemberException extends RuntimeException{
    public InvalidMemberException(final String message) {
        super(message);
    }

    public InvalidMemberException() {
        this("잘못된 회원의 정보입니다.");
    }
}