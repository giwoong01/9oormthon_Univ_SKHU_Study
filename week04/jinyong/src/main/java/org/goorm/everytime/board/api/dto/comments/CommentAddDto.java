package org.goorm.everytime.board.api.dto.comments;


import java.util.Date;

public record CommentAddDto(
        String comment,
        Date timeStamp
) {
}
