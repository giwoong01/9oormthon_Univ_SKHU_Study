package com.example.everytime.post.api;

import com.example.everytime.global.template.RspTemplate;
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
}
