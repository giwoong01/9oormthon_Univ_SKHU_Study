package org.skhu.everytime.community.board.api.dto;

import java.util.List;

public record BoardResponseDto(
        Long id,  // 게시판 ID
        String boardName,  // 게시판 이름
        List<ArticleResponseDto> articles  // 게시물 목록
) {
    public record ArticleResponseDto(
            Long id,  // 게시물 ID
            String title,  // 게시물 제목
            String content,  // 게시물 내용
            String user,  // 작성자
            int recommendCount,  // 추천 수
            int comments  // 댓글 수
    ) {
    }
}
