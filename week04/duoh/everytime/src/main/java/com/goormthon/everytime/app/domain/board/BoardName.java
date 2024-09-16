package com.goormthon.everytime.app.domain.board;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BoardName {
    FREE_BOARD("자유게시판"),
    ANONYMOUS_BOARD("비밀게시판"),
    ALUMNI_BOARD("졸업생게시판"),
    FRESHMAN_BOARD("새내기게시판"),
    CURRENT_ISSUES_BOARD("시사•이슈"),
    INFORMATION_BOARD("정보게시판"),
    CAREER_BOARD("취업•진로"),
    PROMOTION_BOARD("홍보게시판"),
    CLUBS_ACADEMICS_BOARD("동아리•학회"),
    MEDIA_CENTER_BOARD("미디어센터"),
    DORMITORY_BOARD("기숙사게시판");

    private final String displayName;
}
