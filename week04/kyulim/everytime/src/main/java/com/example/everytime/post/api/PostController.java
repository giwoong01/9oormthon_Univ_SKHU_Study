package com.example.everytime.post.api;

import com.example.everytime.global.template.RspTemplate;
import com.example.everytime.post.api.dto.CommentDto;
import com.example.everytime.post.api.dto.request.PostSaveReqDto;
import com.example.everytime.post.api.dto.response.MyPostsResDto;
import com.example.everytime.post.applicatioin.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @Operation(summary = "게시글 등록", description = "게시글 등록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 등록 성공 !"),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
    })
    @PostMapping("/{boardId}/upload")
    public RspTemplate savePost(@PathVariable(name = "boardId") Long boardId, @RequestBody PostSaveReqDto postSaveReqDto) {
        postService.savePost(boardId, postSaveReqDto);
        return new  RspTemplate<>(HttpStatus.CREATED, "게시글 등록 성공");
    }

    // 게시글 조회 -- Comment 구현하고 시작
//    @Operation(summary = "게시글 조회", description = "게시글 조회")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "게시글 조회 성공 !"),
//            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
//    })
//    @GetMapping("")
//    public RspTemplate<PostDetailDto> getPost(
//            @RequestParam Long boardId,
//            @RequestParam Long postId) {
//
//        PostDetailDto postDetail = postService.getPost(boardId, postId);
//        return new RspTemplate<>(HttpStatus.OK, "게시글 조회 성공", postDetail);
//    }

    @Operation(summary = "내가 쓴 글 조회", description = "내가 쓴 글 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "내가 쓴 글 조회 성공 !"),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
    })
    @GetMapping("/myArticle")
    public RspTemplate<MyPostsResDto> getMyPosts(@RequestParam String username) {
        MyPostsResDto myPostsResDto = postService.getMyPosts(username);
        return new RspTemplate<>(HttpStatus.OK, "내가 쓴 글 조회 성공", myPostsResDto);
    }

    @Operation(summary = "게시글 좋아요", description = "게시글 좋아요")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 좋아요 성공 !"),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
    })
    @PostMapping("/{postId}/like")
    public RspTemplate<Void> likePost(@PathVariable Long postId) {
        postService.votePost(postId);
        return new RspTemplate<>(HttpStatus.OK, "좋아요 성공");
    }

    @Operation(summary = "댓글 등록", description = "댓글 등록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 등록 성공 !"),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
    })
    @PostMapping("/{postId}/comment")
    public RspTemplate<Void> addComment(@PathVariable Long postId, @RequestBody CommentDto commentDto) {
        postService.addComment(postId, commentDto);
        return new RspTemplate<>(HttpStatus.OK, "댓글 작성 성공");
    }
}
