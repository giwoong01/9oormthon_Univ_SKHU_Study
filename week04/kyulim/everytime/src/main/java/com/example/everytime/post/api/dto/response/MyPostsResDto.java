package com.example.everytime.post.api.dto.response;

import java.util.List;

public record MyPostsResDto(List<PostDto> posts) {
    public record PostDto(
            Long id,
            String title,
            String content,
            String author,
            int votes,
            int comments
    ) {

    }
}
