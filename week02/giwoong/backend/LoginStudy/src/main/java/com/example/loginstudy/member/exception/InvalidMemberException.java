package com.example.loginstudy.member.exception;

import com.example.loginstudy.global.error.exception.InvalidGroupException;

public class InvalidMemberException extends InvalidGroupException {
    public InvalidMemberException(final String message) {
        super(message);
    }

    public InvalidMemberException() {
        this("잘못된 회원의 정보입니다.");
    }
}
