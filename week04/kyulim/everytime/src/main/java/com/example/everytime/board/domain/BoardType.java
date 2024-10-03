package com.example.everytime.board.domain;

public enum BoardType {
    FREE_BOARD(1, "자유게시판"),
    SECRET_BOARD(2, "비밀게시판"),
    GRADUATE_BOARD(3, "졸업생게시판"),
    FRESHER_BOARD(4, "새내기게시판"),
    ISSUE_BOARD(5, "시사•이슈"),
    INFO_BOARD(6, "정보게시판"),
    CAREER_BOARD(7, "취업•진로"),
    PROMOTION_BOARD(8, "홍보게시판"),
    CLUB_BOARD(9, "동아리•학회"),
    MEDIA_CENTER(10, "미디어센터"),
    DORM_BOARD(11, "기숙사게시판");

    private final int id;
    private final String name;

    BoardType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

