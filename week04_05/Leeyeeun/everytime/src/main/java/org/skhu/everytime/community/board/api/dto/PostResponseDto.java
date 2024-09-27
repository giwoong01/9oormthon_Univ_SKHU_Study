package org.skhu.everytime.community.board.api.dto;

public record PostResponseDto(
        Long boardId,
        String boardName,
        Long postId,
        String postTitle,
        String postContent,
        String user,
        int recommendCount,
        int comment
) {
}
