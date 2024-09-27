package org.skhu.everytime.community.board.domain;

import lombok.Getter;

@Getter
public enum BoardType {
    FREE(1, "자유게시판"),
    SECRET(2, "비밀게시판"),
    GRADUATE(3, "졸업생게시판"),
    FRESHMAN(4, "새내기게시판"),
    ISSUE(5, "시사•이슈"),
    INFO(6, "정보게시판"),
    JOB(7, "취업•진로"),
    PROMOTION(8, "홍보게시판"),
    CLUB(9, "동아리•학회"),
    MEDIA(10, "미디어센터"),
    DORMITORY(11, "기숙사게시판");

    private final int id;
    private final String displayName;

    BoardType(int id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    public int getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }
}
