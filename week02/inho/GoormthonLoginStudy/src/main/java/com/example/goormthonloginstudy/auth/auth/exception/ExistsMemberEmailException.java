package com.example.goormthonloginstudy.auth.auth.exception;

import com.example.goormthonloginstudy.global.error.exception.InvalidGroupException;

public class ExistsMemberEmailException extends InvalidGroupException {

    public ExistsMemberEmailException(String message) {
        super(message);
    }

    public ExistsMemberEmailException() {
        this("이미 가입한 계정이 있는 이메일 입니다.");
    }
}
