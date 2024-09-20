package org.skhu.everytime.community.board.domain;

import lombok.Getter;

@Getter
public enum BoardType {
    FREE("자유게시판"),
    SECRET("비밀게시판"),
    GRADUATE("졸업생게시판"),
    FRESHMAN("새내기게시판"),
    ISSUE("시사•이슈"),
    INFO("정보게시판"),
    JOB("취업•진로"),
    PROMOTION("홍보게시판"),
    CLUB("동아리•학회"),
    MEDIA("미디어센터"),
    DORMITORY("기숙사게시판");

    private final String displayName;

    BoardType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
