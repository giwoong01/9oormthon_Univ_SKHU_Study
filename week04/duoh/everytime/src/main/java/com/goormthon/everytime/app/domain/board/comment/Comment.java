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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int votes;

    @Column(nullable = false)
    private boolean anonym;

    @Builder
    private Comment(Post post, User user, String content, int votes, boolean anonym) {
        this.post = post;
        this.user = user;
        this.content = content;
        this.votes = votes;
        this.anonym = anonym;
    }
}
