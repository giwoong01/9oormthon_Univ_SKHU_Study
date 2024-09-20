package org.goorm.everytime.board.api.dto;

import java.util.List;

public record AllBoardDto(
        List<BoardDto> boards
) {
    public void add(BoardDto board) {
        boards.add(board);
    }
}
