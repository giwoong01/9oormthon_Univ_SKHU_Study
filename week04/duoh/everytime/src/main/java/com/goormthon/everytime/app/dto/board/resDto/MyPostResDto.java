package com.goormthon.everytime.app.dto.board.resDto;

import com.goormthon.everytime.app.domain.board.post.Post;
import lombok.Builder;

@Builder
public record MyPostResDto(
        Long postId,
        String postTitle,
        String postContent,
        String author,
        int likes,
        int comments
) {
    public static MyPostResDto of(Post post, int commentCount) {
        return MyPostResDto.builder()
                .postId(post.getPostId())
                .postTitle(post.getTitle())
                .postContent(post.getContent())
                .author(post.isAnonym() ? "익명" : post.getUser().getNickName())
                .likes(post.getVotes())
                .comments(commentCount)
                .build();
    }
}
