package com.goormthon.everytime.app.dto.board.resDto;

import com.goormthon.everytime.app.domain.board.Board;
import com.goormthon.everytime.global.dto.PageInfoResDto;
import lombok.Builder;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
public record BoardDetailResDto(
        int boardId,
        String boardName,
        PageInfoResDto pageInfo,
        List<PostSummaryResDto> posts
) {
    public static BoardDetailResDto of(Board board, Page<PostSummaryResDto> postPage) {
        return BoardDetailResDto.builder()
                .boardId(board.getBoardName().getId())
                .boardName(board.getBoardName().getDisplayName())
                .pageInfo(PageInfoResDto.from(postPage))
                .posts(postPage.getContent())
                .build();
    }
}
