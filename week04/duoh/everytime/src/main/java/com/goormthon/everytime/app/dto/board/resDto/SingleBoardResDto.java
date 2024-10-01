package com.goormthon.everytime.app.dto.board.resDto;

import com.goormthon.everytime.app.domain.board.Board;
import com.goormthon.everytime.global.dto.PageInfoResDto;
import lombok.Builder;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
public record SingleBoardResDto(
        int boardId,
        String boardName,
        PageInfoResDto pageInfo,
        List<DetailedPostResDto> posts
) {
    public static SingleBoardResDto of(Board board, Page<DetailedPostResDto> postPage) {
        return SingleBoardResDto.builder()
                .boardId(board.getBoardName().getId())
                .boardName(board.getBoardName().getDisplayName())
                .pageInfo(PageInfoResDto.from(postPage))
                .posts(postPage.getContent())
                .build();
    }
}
