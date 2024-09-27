package org.goorm.everytime.board.api.dto.posts;

import org.goorm.everytime.board.domain.Comment;

public record CommentDto(
        Long id,
        String content,
        String username
) {
    public CommentDto(Comment comment) {
        this(
                comment.getId(),
                comment.getCommentContent(),
                comment.getMember().getUsername()
        );
    }
}