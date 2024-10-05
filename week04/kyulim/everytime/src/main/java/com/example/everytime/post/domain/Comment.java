package com.example.everytime.post.domain;

import com.example.everytime.global.entity.BaseEntity;
import com.example.everytime.member.domain.Member;
import com.example.everytime.post.domain.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private String content;
    private String author;

    private int votes;

    @Builder
    public Comment(Post post, Member member, String content, String author, int votes) {
        this.post = post;
        this.member = member;
        this.content = content;
        this.author = author;
        this.votes = votes;
    }
}
