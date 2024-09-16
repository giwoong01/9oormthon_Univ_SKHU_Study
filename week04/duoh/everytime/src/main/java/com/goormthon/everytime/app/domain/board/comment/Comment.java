package com.goormthon.everytime.app.domain.board.comment;

import com.goormthon.everytime.app.domain.BaseEntity;
import com.goormthon.everytime.app.domain.board.post.Post;
import com.goormthon.everytime.app.domain.user.User;
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

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "anonym", nullable = false)
    private boolean anonym;

    @Column(name = "votes")
    private Integer votes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    @Builder
    private Comment(String content, boolean anonym, Integer votes, Post post, User user, Comment parentComment) {
        this.content = content;
        this.anonym = anonym;
        this.votes = votes;
        this.post = post;
        this.user = user;
        this.parentComment = parentComment;
    }
}
