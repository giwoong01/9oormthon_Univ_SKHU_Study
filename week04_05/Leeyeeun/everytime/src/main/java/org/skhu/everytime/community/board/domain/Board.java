package org.skhu.everytime.community.board.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.skhu.everytime.community.board.domain.BoardType;
import org.skhu.everytime.community.board.domain.Post;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_ID")
    @Schema(description = "게시판 ID", example = "1")
    private Long boardId;

    @Enumerated(EnumType.STRING)
    @Schema(description = "게시판 종류", example = "FREE, SECRET")
    private BoardType boardType;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    @Schema(description = "게시판에 속한 게시글 목록")
    private List<Post> posts = new ArrayList<>();

    // 게시판 생성자
    public Board(BoardType boardType) {
        this.boardType = boardType;
    }

    // 게시판에 게시글 추가하는 메소드
    public void addPost(Post post) {
        posts.add(post);
        post.updateBoard(this);
    }
}
