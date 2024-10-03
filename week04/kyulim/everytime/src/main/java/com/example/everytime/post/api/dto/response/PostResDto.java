package com.example.everytime.post.api.dto.response;

public record PostResDto(
        Long postId,
        String title,
        String content,
        String author,
        int votes,
        int comment
) {
}
