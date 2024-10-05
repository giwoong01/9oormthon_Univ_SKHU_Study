package org.goorm.everytime.board.api.dto.comments;

import org.goorm.everytime.board.domain.Comment;

public record CommentDto(
        Long id,
        String content,
        String username
) {
    public CommentDto(Comment comment) {
        this(
                comment.getId(),
                comment.getContent(),
                comment.getMember().getUsername()
        );
    }
}