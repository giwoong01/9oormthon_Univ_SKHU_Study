package org.goorm.everytime.board.api.dto.posts;

import org.goorm.everytime.board.domain.Boards;
import org.goorm.everytime.board.domain.Post;

import java.util.List;
import java.util.stream.Collectors;

public record BoardDto(
        Long id,
        String boardName,
        List<PostDto> posts
) {
    public BoardDto(Boards boards) {
    this(
            boards.getId(),
            boards.getBoardName(),
            boards.getPosts().stream()
                    .map(PostDto::new)
                    .collect(Collectors.toList())
    );
}
}
