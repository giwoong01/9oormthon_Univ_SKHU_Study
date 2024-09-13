package com.example.loginstudy.auth.exception;

import com.example.loginstudy.global.exception.InvalidGroupException;

public class InvalidTypeException extends InvalidGroupException {
    public InvalidTypeException(String message) {
        super(message);
    }
    public InvalidTypeException() {
        this("잘못된 타입입니다");
    }
}
