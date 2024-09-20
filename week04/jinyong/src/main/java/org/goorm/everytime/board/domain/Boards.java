package org.goorm.everytime.board.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/*
 * 게시판 테이블
 * 각 게시판은 번호별로 구분
1. 자유게시판
2. 비밀게시판
3. 졸업생게시판
4. 새내기게시판
5. 시사•이슈
6. 정보게시판
7. 취업•진로
8. 홍보게시판
9. 동아리•학회
10. 미디어센터
11. 기숙사게시판
 */

@Getter
@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Boards {

    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "board_name")
    private String boardName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "board")
    private List<Post> posts = new ArrayList<>();


    @Builder
    public Boards(String boardName) {
        this.boardName = boardName;
    }
}
