package com.example.everytime.post.api.dto.request;

public record PostSaveReqDto(
        String title,
        String content,
        String anonym
) {
}
