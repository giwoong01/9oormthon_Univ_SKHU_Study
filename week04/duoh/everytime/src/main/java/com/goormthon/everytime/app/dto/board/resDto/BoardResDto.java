package com.goormthon.everytime.app.dto.board.resDto;

import com.goormthon.everytime.app.domain.board.Board;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public record BoardResDto(
        Long boardId,
        String boardName,
        List<PostResDto> posts
) {
    public static BoardResDto from(Board board) {
        return BoardResDto.builder()
                .boardId(board.getBoardId())
                .boardName(board.getBoardName().getDisplayName())
                .posts(board.getPosts().stream()
                        .map(PostResDto::from)
                        .collect(Collectors.toList()))
                .build();
    }
}
