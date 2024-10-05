package com.example.everytime.post.api;

import com.example.everytime.global.template.RspTemplate;
import com.example.everytime.member.domain.Member;
import com.example.everytime.post.api.dto.request.CommentReqDto;
import com.example.everytime.post.api.dto.request.PostSaveReqDto;
import com.example.everytime.post.api.dto.response.MyPostsResDto;
import com.example.everytime.post.api.dto.response.PostResDto;
import com.example.everytime.post.applicatioin.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @Operation(summary = "게시글 등록", description = "게시글 등록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 등록 성공 !"),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
    })
    @PostMapping("/boards/{boardId}/upload")
    public RspTemplate<String> savePost(
            @PathVariable(name = "boardId") Long boardId,
            @AuthenticationPrincipal Member member,
            @RequestBody PostSaveReqDto postSaveReqDto) {
        postService.savePost(boardId, member, postSaveReqDto);
        return new  RspTemplate<>(HttpStatus.CREATED, "게시글 등록 성공");
    }

    @Operation(summary = "내가 쓴 글 조회", description = "내가 쓴 글 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "내가 쓴 글 조회 성공 !"),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
    })
    @GetMapping("/myPost")
        public RspTemplate<MyPostsResDto> getMyPosts(@AuthenticationPrincipal Member member) {
        MyPostsResDto myPostsResDto = postService.getMyPosts(member);
        return new RspTemplate<>(HttpStatus.OK, "내가 쓴 글 조회 성공", myPostsResDto);
    }

//    @Operation(summary = "게시글 조회", description = "게시글 조회")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "게시글 조회 성공 !"),
//            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
//    })
//    @GetMapping()
//    public RspTemplate<PostResDto> getPost(@PathVariable Long boardId, @PathVariable Long postId) {
//        return postService.getPost(boardId, postId);
//    }


    @Operation(summary = "게시글 좋아요", description = "게시글 좋아요")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 좋아요 성공 !"),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
    })
    @PostMapping("/boards/{boardId}/{postId}/likes")
    public RspTemplate<Void> likePost(@PathVariable Long boardId, @PathVariable Long postId, @AuthenticationPrincipal Member member) {
        postService.votePost(postId, member);
        return new RspTemplate<>(HttpStatus.OK, "좋아요 성공");
    }

    @Operation(summary = "댓글 등록", description = "댓글 등록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글 등록 성공 !"),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
    })
    @PostMapping("/boards/{boardId}/{postId}/comment")
    public RspTemplate<Void> addComment(@PathVariable Long boardId, @PathVariable Long postId, @RequestBody CommentReqDto commentDto, @AuthenticationPrincipal Member member) {
        postService.addComment(postId, commentDto, member);
        return new RspTemplate<>(HttpStatus.OK, "댓글 작성 성공");
    }

    @Operation(summary = "스크랩 추가", description = "스크랩 추가")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "스크랩 추가 성공 !"),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
    })
    @PostMapping("/boards/{boardId}/{postId}/scrap")
    public RspTemplate<Void> addScrap(@PathVariable Long boardId, @PathVariable Long postId, @AuthenticationPrincipal Member member) {
        postService.addScrap(boardId, postId, member);
        return new RspTemplate<>(HttpStatus.OK, "스크랩 추가 성공");
    }
}
