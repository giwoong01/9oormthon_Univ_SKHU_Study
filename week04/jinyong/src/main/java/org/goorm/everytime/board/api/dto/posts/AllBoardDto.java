package org.goorm.everytime.board.api.dto.posts;

import org.goorm.everytime.board.domain.Boards;

import java.util.List;

public record AllBoardDto(
        List<BoardDto> board
) {
//    public void add(BoardDto boardDto) {
//        board.add(boardDto);
//    }
}
