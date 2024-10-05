package com.example.everytime.post.domain;

import com.example.everytime.board.domain.Board;
import com.example.everytime.global.entity.BaseEntity;
import com.example.everytime.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    private int scraps;

    // files 추가해야 함

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(mappedBy = "post")
    private List<Comment> commentList = new ArrayList<>();

    @Builder
    private Post(String title, String content, String anonym, String author, int votes, int comments, int scraps, Member member, Board board) {
        this.title = title;
        this.content = content;
        this.anonym = anonym;
        this.author = author;
        this.votes = votes;
        this.comments = comments;
        this.scraps = scraps;
        this.member = member;
        this.board = board;
    }

    public boolean isAnonym() {
        return Boolean.parseBoolean(this.anonym);
    }

    public void incrementVotes() {
        this.votes++;
    }

    public void decrementVotes() {
        this.votes--;
    }

    public void incrementComments() {
        this.comments++;
    }

    public void decrementComments() {
        this.comments--;
    }

    public void incrementScraps() {
        this.scraps++;
    }
}
