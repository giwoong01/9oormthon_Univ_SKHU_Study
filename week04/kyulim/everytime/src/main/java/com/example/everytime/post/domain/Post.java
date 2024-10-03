package com.example.everytime.post.domain;

import com.example.everytime.board.domain.Board;
import com.example.everytime.global.entity.BaseEntity;
import com.example.everytime.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    private String title;

    private String content;

    private String anonym;

    private String author;

    private int votes;

    private int comments;

    // files 추가해야 함

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    private Post(String title, String content, String anonym, String author, int votes, int comments, Member member, Board board) {
        this.title = title;
        this.content = content;
        this.anonym = anonym;
        this.author = author;
        this.votes = votes;
        this.comments = comments;
        this.member = member;
        this.board = board;
    }

    public boolean isAnonym() {
        return Boolean.parseBoolean(this.anonym);
    }
}
