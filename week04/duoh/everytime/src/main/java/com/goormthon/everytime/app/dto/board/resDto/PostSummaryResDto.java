package com.goormthon.everytime.app.dto.board.resDto;

import com.goormthon.everytime.app.domain.board.post.Post;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
public record PostSummaryResDto(
        Long postId,
        String postTitle,
        String postContent,
        String author,
        int likes,
        int comments,
        String timestamp
) {
    public static PostSummaryResDto of(Post post, int commentCount) {
        return PostSummaryResDto.builder()
                .postId(post.getPostId())
                .postTitle(post.getTitle())
                .postContent(post.getContent())
                .author(post.isAnonym() ? "익명" : post.getUser().getNickName())
                .likes(post.getVotes())
                .comments(commentCount)
                .timestamp(formatTimestamp(post.getCreatedAt()))
                .build();
    }

    private static String formatTimestamp(LocalDateTime createdAt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        return createdAt.format(formatter);
    }
}
