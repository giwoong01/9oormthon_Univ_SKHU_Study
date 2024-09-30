package com.goormthon.everytime.app.dto.board.reqDto;

import com.goormthon.everytime.app.domain.board.Board;
import com.goormthon.everytime.app.domain.board.post.Post;
import com.goormthon.everytime.app.domain.user.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
public record PostReqDto(
        @NotBlank(message = "제목이 공백일 수 없습니다.")
        String title,

        @NotBlank(message = "내용이 공백일 수 없습니다.")
        String content,

        @NotBlank(message = "익명 여부가 공백일 수 없습니다.")
        String anonym,

        List<MultipartFile> files
) {

    public Post toEntity(Board board, User user) {
        return Post.builder()
                .title(title)
                .content(content)
                .anonym(Boolean.parseBoolean(anonym))
                .board(board)
                .user(user)
                .build();
    }
}
