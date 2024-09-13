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
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    INVALID_SIGNATURE(HttpStatus.UNAUTHORIZED, "유효하지 않은 서명입니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),

    // 403 Forbidden
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "권한이 없습니다."),

    // 404 NOT FOUND

    // 409 Conflict

    // 500 Internal Server Exception

    // 503 Service Unavailable
    ;
    private final HttpStatus httpStatus;
    private final String message;
}
