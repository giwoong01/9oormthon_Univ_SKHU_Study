package com.example.everytime.board.api.dto;

import com.example.everytime.post.api.dto.response.PostResDto;

import java.util.List;


public record BoardListResDto(List<BoardDto> boards) {

    public record BoardDto(
            Long boardId,
            String boardName,
            List<PostResDto> posts) {
    }
}
