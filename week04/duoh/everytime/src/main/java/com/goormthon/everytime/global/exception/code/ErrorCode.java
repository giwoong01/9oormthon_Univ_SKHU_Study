package com.goormthon.everytime.global.exception.code;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {

    // 400 Bad Request
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "유효성 검사에 맞지 않습니다."),
    JSON_PARSE_ERROR(HttpStatus.BAD_REQUEST, "JSON 파싱 중 오류가 발생했습니다."),

    // 401 Unauthorized

    // 403 Forbidden

    // 404 NOT FOUND

    // 409 Conflict

    // 500 Internal Server Exception

    // 503 Service Unavailable
    ;
    private final HttpStatus httpStatus;
    private final String message;
}
