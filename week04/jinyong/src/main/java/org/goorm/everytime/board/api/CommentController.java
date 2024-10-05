package org.goorm.everytime.board.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import org.goorm.everytime.board.api.dto.comments.CommentAddDto;
import org.goorm.everytime.board.service.CommentService;
import org.goorm.everytime.board.service.PostProvider;
import org.goorm.everytime.global.common.dto.BaseResponse;
import org.goorm.everytime.global.common.exception.SuccessCode;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final PostProvider postProvider;

    @Operation(summary = "댓글 추가", description = "게시글에 댓글을 추가한다.")
    @Parameters({
            @Parameter(name = "boardId", description = "게시판 ID", required = true),
            @Parameter(name = "postId", description = "게시글 ID", required = true),
            @Parameter(name = "comment", description = "댓글 내용", required = true)
    })
    @PostMapping("/boards/{boardId}/{postId}/comments")
    BaseResponse addComment(@RequestBody CommentAddDto commentAddDto, @PathVariable Long boardId, @PathVariable Long postId, Principal principal) {
        commentService.addComment(commentAddDto, postId, principal);
        return BaseResponse.success(SuccessCode.ADD_COMMENT);
    }

    @GetMapping("/myComment")
    BaseResponse provideMyComments(Principal principal) {
        return BaseResponse.success(SuccessCode.GET_MY_COMMENTS, postProvider.provideMyPosts(principal));
    }
}
