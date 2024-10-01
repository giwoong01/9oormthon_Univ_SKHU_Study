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
    GET_TOKEN_SUCCESS(HttpStatus.OK, "토큰을 성공적으로 가져왔습니다."),
    GET_USER_INFO_SUCCESS(HttpStatus.OK, "사용자 프로필 조회에 성공했습니다."),
    GET_BOARD_LIST_SUCCESS(HttpStatus.OK, "전체 게시판 조회에 성공했습니다."),
    GET_SINGLE_BOARD_SUCCESS(HttpStatus.OK, "개별 게시판 조회에 성공했습니다."),
    GET_POST_SUCCESS(HttpStatus.OK, "게시글 조회에 성공했습니다."),
    GET_MY_POSTS_SUCCESS(HttpStatus.OK, "내 게시글 조회에 성공했습니다."),

    // 201 Created
    SIGNUP_USER_SUCCESS(HttpStatus.CREATED, "회원가입에 성공했습니다."),
    POST_CREATED_SUCCESS(HttpStatus.CREATED, "게시글 등록에 성공했습니다."),
    COMMENT_CREATED_SUCCESS(HttpStatus.CREATED, "댓글 등록에 성공했습니다.");

    // 204 No Content

    private final HttpStatus httpStatus;
    private final String message;
}
