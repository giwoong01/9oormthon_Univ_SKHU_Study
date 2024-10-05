package com.goormthon.everytime.app.dto.board.resDto;

import com.goormthon.everytime.app.domain.board.comment.Comment;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
public record CommentResDto(
        Long commentId,
        Long commentAuthorId,
        String commentAuthor,
        String commentContent,
        String timestamp,
        int likes
) {
    public static CommentResDto from(Comment comment) {
        return CommentResDto.builder()
                .commentId(comment.getCommentId())
                .commentAuthorId(comment.getUser().getUserId())
                .commentAuthor(comment.isAnonym() ? "익명" : comment.getUser().getNickName())
                .commentContent(comment.getContent())
                .timestamp(formatTimestamp(comment.getCreatedAt()))
                .likes(comment.getVotes())
                .build();
    }

    private static String formatTimestamp(LocalDateTime createdAt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        return createdAt.format(formatter);
    }
}
