package com.goormthon.everytime.app.domain.board.post;

import com.goormthon.everytime.app.domain.BaseEntity;
import com.goormthon.everytime.app.domain.board.Board;
import com.goormthon.everytime.app.domain.board.comment.Comment;
import com.goormthon.everytime.app.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private boolean anonym;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int votes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @Builder
    private Post(String title, String content, boolean anonym, int votes, Board board, User user) {
        this.title = title;
        this.content = content;
        this.anonym = anonym;
        this.votes = votes;
        this.board = board;
        this.user = user;
    }
}

