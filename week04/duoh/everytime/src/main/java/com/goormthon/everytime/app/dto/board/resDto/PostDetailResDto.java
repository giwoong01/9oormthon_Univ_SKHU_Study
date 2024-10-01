package com.goormthon.everytime.app.dto.board.resDto;

import com.goormthon.everytime.app.domain.board.post.Post;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Builder
public record PostDetailResDto(
        int boardId,
        String boardName,
        Long postId,
        Long authorId,
        String postTitle,
        String postContent,
        String author,
        String timestamp,
        int likes,
        int commentsCount,
        int scrapsCount,
        List<CommentResDto> comments
) {
    public static PostDetailResDto of(Post post, int commentsCount, List<CommentResDto> comments) {
        return PostDetailResDto.builder()
                .boardId(post.getBoard().getBoardName().getId())
                .boardName(post.getBoard().getBoardName().getDisplayName())
                .postId(post.getPostId())
                .authorId(post.getUser().getUserId())
                .postTitle(post.getTitle())
                .postContent(post.getContent())
                .author(post.isAnonym() ? "익명" : post.getUser().getNickName())
                .timestamp(formatTimestamp(post.getCreatedAt()))
                .likes(post.getVotes())
                .commentsCount(commentsCount)
                .scrapsCount(0)
                .comments(comments)
                .build();
    }

    private static String formatTimestamp(LocalDateTime createdAt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        return createdAt.format(formatter);
    }
}
