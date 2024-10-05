package com.goormthon.everytime.app.dto.board.resDto;

import com.goormthon.everytime.app.domain.board.Board;
import lombok.Builder;

import java.util.List;

@Builder
public record MyBoardResDto(
        int boardId,
        String boardName,
        List<MyPostResDto> posts
) {
    public static MyBoardResDto of(Board board, List<MyPostResDto> myPost) {
        return MyBoardResDto.builder()
                .boardId(board.getBoardName().getId())
                .boardName(board.getBoardName().getDisplayName())
                .posts(myPost)
                .build();
    }
}
