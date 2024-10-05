package com.example.everytime.post.api.dto.response;

public record CommentResDto(
        Long commentId,
        String commentAuthor,
        String commentContent,
        String timestamp,
        int likes
) {

}
