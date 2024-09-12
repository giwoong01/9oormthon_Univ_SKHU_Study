package com.example.loginstudy.user.exception;

import com.example.loginstudy.global.exception.NotFoundGroupException;

public class UserNotFoundException extends NotFoundGroupException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException() {
        this("존재하지 않는 회원입니다.");
    }
}
