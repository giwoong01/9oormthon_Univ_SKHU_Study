package com.goormthon.everytime.global.exception.code;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessCode {

    // 200 OK

    // 201 Created

    // 204 No Content
    ;
    private final HttpStatus httpStatus;
    private final String message;
}
