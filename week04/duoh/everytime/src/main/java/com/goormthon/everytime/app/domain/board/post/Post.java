package com.goormthon.everytime.app.domain.board.post;

import com.goormthon.everytime.app.domain.BaseEntity;
import com.goormthon.everytime.app.domain.board.Board;
import com.goormthon.everytime.app.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "anonym", nullable = false)
    private boolean anonym;

    @Column(name = "votes", nullable = false, columnDefinition = "int default 0")
    private int votes;

    @Column(name = "comments", nullable = false, columnDefinition = "int default 0")
    private int comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    private Post(String title, String content, boolean anonym, int votes, int comments, Board board, User user) {
        this.title = title;
        this.content = content;
        this.anonym = anonym;
        this.votes = votes;
        this.comments = comments;
        this.board = board;
        this.user = user;
    }
}

