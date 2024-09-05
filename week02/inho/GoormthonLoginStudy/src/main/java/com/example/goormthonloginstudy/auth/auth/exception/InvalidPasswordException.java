package com.example.goormthonloginstudy.auth.auth.exception;

import com.example.goormthonloginstudy.global.error.exception.AuthGroupException;

public class InvalidPasswordException extends AuthGroupException {

    public InvalidPasswordException(String message) {
        super(message);
    }

    public InvalidPasswordException() {
        this("비밀번호가 유효하지 않습니다.");
    }
}
