package org.goorm.everytime.board.api.dto.member_comments;

import org.goorm.everytime.board.domain.Post;

import java.util.List;

public record MyPostsDto(
        List<Post> posts
) {
}
