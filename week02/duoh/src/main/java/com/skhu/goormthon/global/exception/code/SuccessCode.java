package com.skhu.goormthon.global.exception.code;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessCode {

    // 200 OK
    GET_TOKEN_SUCCESS(HttpStatus.OK, "Access 토큰을 성공적으로 가져왔습니다."),
    RENEW_TOKEN_SUCCESS(HttpStatus.OK, "Access 토큰을 성공적으로 재발급했습니다."),
    LOGIN_MEMBER_SUCCESS(HttpStatus.OK, "로그인에 성공했습니다."),
    GET_MEMBER_INFO_SUCCESS(HttpStatus.OK, "사용자 정보 조회에 성공했습니다."),
    EMAIL_SEND_CODE_SUCCESS(HttpStatus.OK, "인증 코드를 성공적으로 전송했습니다."),
    EMAIL_VERIFICATION_SUCCESS(HttpStatus.OK, "이메일 인증에 성공했습니다."),

    // 201 Created
    SIGNUP_MEMBER_SUCCESS(HttpStatus.CREATED, "회원가입에 성공했습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
