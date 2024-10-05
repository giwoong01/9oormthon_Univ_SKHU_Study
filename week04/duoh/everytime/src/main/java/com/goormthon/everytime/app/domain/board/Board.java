package com.goormthon.everytime.app.domain.board;

import com.goormthon.everytime.app.domain.board.post.Post;
import com.goormthon.everytime.app.domain.user.University;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long boardId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 100)
    private BoardName boardName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private University university;

    @OneToMany(mappedBy = "board")
    private List<Post> posts;

    @Builder
    private Board(BoardName boardName, University university) {
        this.boardName = boardName;
        this.university = university;
    }
}
