package com.goormthon.everytime.app.dto.board.resDto;

import com.goormthon.everytime.app.domain.board.Board;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public record BoardListResDto(
        int boardId,
        String boardName,
        List<PostResDto> posts
) {
    public static BoardListResDto from(Board board) {
        return BoardListResDto.builder()
                .boardId(board.getBoardName().getId())
                .boardName(board.getBoardName().getDisplayName())
                .posts(board.getPosts().stream()
                        .map(PostResDto::from)
                        .collect(Collectors.toList()))
                .build();
    }
}
