package com.goormthon.everytime.app.dto.board.resDto;

import com.goormthon.everytime.app.domain.board.post.Post;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
public record DetailedPostResDto(
        Long postId,
        String postTitle,
        String postContent,
        String author,
        int likes,
        int comments,
        String timestamp
) {
    public static DetailedPostResDto from(Post post) {
        return DetailedPostResDto.builder()
                .postId(post.getPostId())
                .postTitle(post.getTitle())
                .postContent(post.getContent())
                .author(post.isAnonym() ? "익명" : post.getUser().getNickName())
                .likes(post.getVotes())
                .comments(post.getComments())
                .timestamp(formatTimestamp(post.getCreatedAt()))
                .build();
    }

    private static String formatTimestamp(LocalDateTime createdAt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        return createdAt.format(formatter);
    }
}
