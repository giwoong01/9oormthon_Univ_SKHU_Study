package com.goormthon.everytime.app.dto.board.resDto;

import lombok.Builder;

import java.util.List;

@Builder
public record BoardDetailWrapperResDto(
        List<BoardDetailResDto> boards
) {
    public static BoardDetailWrapperResDto from(List<BoardDetailResDto> boardDetail) {
        return BoardDetailWrapperResDto.builder()
                .boards(boardDetail)
                .build();
    }
}
