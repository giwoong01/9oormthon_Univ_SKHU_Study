package com.example.everytime.post.api.dto.response;

public record PostSaveResDto(
        Long postId,
        String title,
        String content,
        String anonym
) {
}
