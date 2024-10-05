package com.goormthon.everytime.app.dto.board.resDto;

import com.goormthon.everytime.app.domain.board.Board;
import lombok.Builder;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
public record BoardDetailResDto(
        int boardId,
        String boardName,
        int currentPage,
        int totalPages,
        long totalItems,
        List<PostSummaryResDto> posts
) {
    public static BoardDetailResDto of(Board board, Page<PostSummaryResDto> postPage) {
        return BoardDetailResDto.builder()
                .boardId(board.getBoardName().getId())
                .boardName(board.getBoardName().getDisplayName())
                .currentPage(postPage.getNumber() + 1)
                .totalPages(postPage.getTotalPages())
                .totalItems(postPage.getTotalElements())
                .posts(postPage.getContent())
                .build();
    }
}
