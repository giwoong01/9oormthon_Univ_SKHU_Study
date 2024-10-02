package com.goormthon.everytime.app.dto.board.resDto;

import lombok.Builder;

import java.util.List;

@Builder
public record BoardWrapperResDto(
        List<MyBoardResDto> boards
) {
    public static BoardWrapperResDto from(List<MyBoardResDto> myBoard) {
        return BoardWrapperResDto.builder()
                .boards(myBoard)
                .build();
    }
}
