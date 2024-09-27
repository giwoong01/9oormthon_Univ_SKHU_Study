package org.goorm.everytime.global.common.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessCode {

    /*201 CREATED*/
    USER_JOIN_SUCCESS(HttpStatus.CREATED, "유저 회원가입 성공"),


    /*200 OK*/
    USER_LOGIN_SUCCESS(HttpStatus.OK, "유저 로그인 성공"),
    USER_PROFILE_SUCCESS(HttpStatus.OK, "유저 프로필 조회 성공"),
    USER_DELETE_SUCCESS(HttpStatus.OK, "회원 탈퇴 완료"),
    GET_POSTS_OF_BOARD(HttpStatus.OK, "개별 게시판 글 조회 성공"),
    GET_ALL_BOARD(HttpStatus.OK, "전체 게시판 조회 성공"),
    POST_UPLOADED(HttpStatus.OK, "게시글 업로드 성공"),
    GET_MY_POSTS(HttpStatus.OK, "내가 쓴 글 조회 성공"),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    public int getHttpStatusCode(){
        return httpStatus.value();
    }
}