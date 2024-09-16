package com.goormthon.everytime.app.domain.board;

import com.goormthon.everytime.app.domain.user.university.University;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long boardId;

    @Enumerated(EnumType.STRING)
    @Column(name = "board_name", nullable = false, length = 100)
    private BoardName boardName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id", nullable = false)
    private University university;

    @Builder
    private Board(BoardName boardName, University university) {
        this.boardName = boardName;
        this.university = university;
    }
}
