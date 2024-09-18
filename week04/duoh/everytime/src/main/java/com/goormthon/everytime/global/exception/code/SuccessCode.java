package com.goormthon.everytime.global.exception.code;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessCode {

    // 200 OK
    LOGIN_USER_SUCCESS(HttpStatus.OK, "로그인에 성공했습니다."),

    // 201 Created
    SIGNUP_USER_SUCCESS(HttpStatus.CREATED, "회원가입에 성공했습니다.");

    // 204 No Content

    private final HttpStatus httpStatus;
    private final String message;
}
