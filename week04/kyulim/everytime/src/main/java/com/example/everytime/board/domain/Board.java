package com.example.everytime.board.domain;

import com.example.everytime.global.entity.BaseEntity;
import com.example.everytime.post.domain.Post;
import com.example.everytime.member.domain.UniversityName;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long boardId;

    private String boardName;

    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    @OneToMany(mappedBy = "board")
    private List<Post> posts = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private UniversityName universityName;

    public Board(Long boardId, String boardName, BoardType boardType, UniversityName universityName) {
        this.boardId = boardId;
        this.boardName = boardName;
        this.boardType = boardType;
        this.universityName = universityName;
    }
}
