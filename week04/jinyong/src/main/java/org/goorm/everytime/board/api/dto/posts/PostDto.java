package org.goorm.everytime.board.api.dto.posts;

import org.goorm.everytime.board.domain.Post;

import java.util.List;
import java.util.stream.Collectors;

public record PostDto(
        Long id,
        String title,
        String content,
        String username,
        List<CommentDto> comments
) {
    public PostDto(Post post) {
        this(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getMember().getUsername(),
                post.getCommentList().stream()
                        .map(CommentDto::new)
                        .collect(Collectors.toList())
        );
    }
}