package com.skhu.goormthon.global.exception.code;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {

    // 400 Bad Request
    PASSWORD_MISMATCH_EXCEPTION(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    INVALID_SIGNATURE_EXCEPTION(HttpStatus.BAD_REQUEST, "JWT 토큰의 서명이 올바르지 않습니다."),
    VALIDATION_EXCEPTION(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    JSON_SYNTAX_EXCEPTION(HttpStatus.BAD_REQUEST, "JSON 파싱 오류 발생"),
    EMAIL_VERIFICATION_CODE_MISMATCH_EXCEPTION(HttpStatus.BAD_REQUEST, "인증 코드가 일치하지 않습니다."),
    EMAIL_NOT_VERIFIED_EXCEPTION(HttpStatus.BAD_REQUEST, "이메일 인증이 완료되지 않았습니다."),

    // 401 Unauthorized
    INVALID_TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),

    // 403 Forbidden
    FORBIDDEN_AUTH_EXCEPTION(HttpStatus.FORBIDDEN, "권한 정보가 없는 토큰입니다."),
    EXPIRED_TOKEN_EXCEPTION(HttpStatus.FORBIDDEN, "토큰이 만료되었습니다."),

    // 404 NOT FOUND
    NOT_FOUND_EMAIL_EXCEPTION(HttpStatus.NOT_FOUND, "이메일을 찾을 수 없습니다."),
    NOT_FOUND_MEMBER_EXCEPTION(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),

    // 409 Conflict
    ALREADY_EXIST_MEMBER_EXCEPTION(HttpStatus.CONFLICT, "해당 이메일로 가입된 계정이 존재합니다. 회원가입하신 플랫폼을 통해 로그인을 시도해주세요."),

    // 500 Internal Server Exception
    INTERNAL_SERVER_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 서버 에러가 발생했습니다."),
    EMAIL_SEND_FAILURE_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "이메일 전송에 실패했습니다."),

    // 503 Service Unavailable
    FAILED_GET_TOKEN_EXCEPTION(HttpStatus.SERVICE_UNAVAILABLE, "토큰을 가져오는 중 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
