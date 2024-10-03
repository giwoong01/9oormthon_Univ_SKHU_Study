package com.example.everytime.board.api.dto;

import com.example.everytime.post.api.dto.response.PostResDto;

import java.util.List;

public record BoardResDto(
        Long id,
        String boardName,
        List<PostResDto> posts
) {
}
