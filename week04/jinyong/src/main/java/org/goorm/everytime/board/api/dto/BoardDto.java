package org.goorm.everytime.board.api.dto;

import org.goorm.everytime.board.domain.Post;

import java.util.List;

public record BoardDto(
        Long id,
        String boardName,
        List<Post> articles
) {
}
