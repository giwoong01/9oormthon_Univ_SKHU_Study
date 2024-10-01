package com.goormthon.everytime.app.dto.board.reqDto;

import com.goormthon.everytime.app.domain.board.comment.Comment;
import com.goormthon.everytime.app.domain.board.post.Post;
import com.goormthon.everytime.app.domain.user.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CommentReqDto(
        @NotBlank(message = "댓글이 공백일 수 없습니다.")
        String comment,
        String timeStamp
) {
    public Comment toEntity(Post post, User user) {
        return Comment.builder()
                .post(post)
                .user(user)
                .content(this.comment)
                .build();
    }
}
