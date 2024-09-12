package com.example.loginstudy.user.exception;

import com.example.loginstudy.global.exception.InvalidGroupException;

public class InvalidPasswordException extends InvalidGroupException {
    public InvalidPasswordException(String message) {
        super(message);
    }
    public InvalidPasswordException() {
        this("유효하지 않는 비밀번호입니다");
    }
}
