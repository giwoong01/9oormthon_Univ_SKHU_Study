package com.goormthon.everytime.app.dto.board.resDto;

import lombok.Builder;

import java.util.List;

@Builder
public record BoardListWrapperResDto(
        List<BoardListResDto> boards
) {
    public static BoardListWrapperResDto from(List<BoardListResDto> boardList) {
        return BoardListWrapperResDto.builder()
                .boards(boardList)
                .build();
    }
}
