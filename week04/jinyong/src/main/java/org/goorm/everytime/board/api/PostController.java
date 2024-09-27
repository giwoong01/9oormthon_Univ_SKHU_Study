package org.goorm.everytime.board.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import org.goorm.everytime.board.api.dto.member_comments.MyPostsDto;
import org.goorm.everytime.board.api.dto.posts.PostUploadDto;
import org.goorm.everytime.board.service.PostProvider;
import org.goorm.everytime.board.service.PostUploader;
import org.goorm.everytime.global.common.dto.BaseResponse;
import org.goorm.everytime.global.common.exception.SuccessCode;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostProvider postProvider;
    private final PostUploader postUploader;

    @Operation(summary = "특정 게시판의 게시글 조회", description = "게시판의 게시글을 조회한다.")
    @Parameter(name = "boardId", description = "게시판 ID", required = true)
    @GetMapping("/boards/{boardId}")
    public BaseResponse providePostsOfBoard(@PathVariable Long boardId) {
        return BaseResponse.success(SuccessCode.GET_POSTS_OF_BOARD, postProvider.providePostsOfBoard(boardId));
    }

    @Operation(summary = "게시판의 게시글 전체 조회", description = "모든 게시판을 조회한다.")
    @GetMapping("/boards")
    public BaseResponse provideAllBoard() {
        return BaseResponse.success(SuccessCode.GET_ALL_BOARD, postProvider.provideAllBoard());
    }

    @Operation(summary = "게시글 업로드", description = "게시글을 업로드한다.")
    @Parameters({
            @Parameter(name = "boardId", description = "게시판 ID, query parameter로 보낸다.", required = true),
            @Parameter(name = "title", description = "게시글 제목", required = true),
            @Parameter(name = "content", description = "게시글 내용", required = true),
            @Parameter(name = "anonym", description = "익명 여부", required = true),
            @Parameter(name = "files", description = "파일", required = false)
    })
    @PostMapping("/boards/{boardId}/upload")
    public BaseResponse uploadPost(@ModelAttribute PostUploadDto postUploadDto, Principal principal) {
        postUploader.uploadPost(postUploadDto, principal);
        return BaseResponse.success(SuccessCode.POST_UPLOADED);
    }

    @Operation(summary = "내가 쓴 글 조회", description = "내가 쓴 글을 조회한다.")
    @GetMapping("/myArticle")
    public BaseResponse<MyPostsDto> provideMyPosts(Principal principal) {
        return BaseResponse.success(SuccessCode.GET_MY_POSTS, postProvider.provideMyPosts(principal));
    }
}
