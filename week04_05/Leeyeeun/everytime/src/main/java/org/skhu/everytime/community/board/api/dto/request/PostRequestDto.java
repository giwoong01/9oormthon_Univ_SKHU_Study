package org.skhu.everytime.community.board.api.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record PostRequestDto(
        @NotBlank String title,   // 게시글 제목
        @NotBlank String content, // 게시글 내용
        String anony,            // 익명 여부
        List<String> files       // 파일 목록

) {
}
