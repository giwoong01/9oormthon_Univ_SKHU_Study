package com.goormthon.everytime.app.domain.board;

import com.goormthon.everytime.global.exception.CustomException;
import com.goormthon.everytime.global.exception.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum BoardName {
    FREE_BOARD(1, "자유게시판"),
    ANONYMOUS_BOARD(2, "비밀게시판"),
    ALUMNI_BOARD(3, "졸업생게시판"),
    FRESHMAN_BOARD(4, "새내기게시판"),
    CURRENT_ISSUES_BOARD(5, "시사•이슈"),
    INFORMATION_BOARD(6, "정보게시판"),
    CAREER_BOARD(7, "취업•진로"),
    PROMOTION_BOARD(8, "홍보게시판"),
    CLUBS_ACADEMICS_BOARD(9, "동아리•학회"),
    MEDIA_CENTER_BOARD(10, "미디어센터"),
    DORMITORY_BOARD(11, "기숙사게시판");

    private final int id;
    private final String displayName;

    public static BoardName fromId(int id) {
        return Arrays.stream(BoardName.values())
                .filter(boardName -> boardName.id == id)
                .findFirst()
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_BOARD, ErrorCode.INVALID_BOARD.getMessage()));
    }
}
