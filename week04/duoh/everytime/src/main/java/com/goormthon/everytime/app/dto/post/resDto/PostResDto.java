package com.goormthon.everytime.app.dto.post.resDto;

import com.goormthon.everytime.app.domain.board.post.Post;
import lombok.Builder;

@Builder
public record PostResDto(
        Long postId,
        String postTitle,
        String postContent
) {
    public static PostResDto from(Post post) {
        return PostResDto.builder()
                .postId(post.getPostId())
                .postTitle(post.getTitle())
                .postContent(post.getContent())
                .build();
    }
}
