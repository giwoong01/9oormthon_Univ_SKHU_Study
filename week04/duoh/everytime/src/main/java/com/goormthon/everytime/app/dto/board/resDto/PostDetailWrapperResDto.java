package com.goormthon.everytime.app.dto.board.resDto;

import lombok.Builder;

import java.util.List;

@Builder
public record PostDetailWrapperResDto(
        List<PostDetailResDto> posts
) {
    public static PostDetailWrapperResDto from(List<PostDetailResDto> postDetail) {
        return PostDetailWrapperResDto.builder()
                .posts(postDetail)
                .build();
    }
}
