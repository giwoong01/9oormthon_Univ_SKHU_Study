package org.skhu.everytime.community.board.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.skhu.everytime.community.board.api.dto.PostResponseDto;
import org.skhu.everytime.community.board.application.BoardService;
import org.skhu.everytime.community.board.application.PostService;
import org.skhu.everytime.community.board.domain.repository.BoardRepository;
import org.skhu.everytime.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @Operation(summary = "내가 작성한 글 조회", description = "사용자가 작성한 게시물을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "인증 실패", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @GetMapping("/myArticle")
    public ResponseEntity<Map<String, List<PostResponseDto>>> getMyArticles(@AuthenticationPrincipal User user) {
        List<PostResponseDto> myArticles = postService.getMyArticles(user);
        return ResponseEntity.ok(Map.of("data", myArticles));
    }

    @Operation(summary = "내가 댓글 단 글 조회", description = "사용자가 댓글을 단 게시물을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "인증 실패", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @GetMapping("/myComment")
    public ResponseEntity<Map<String, List<PostResponseDto>>> getMyCommentedPosts(@AuthenticationPrincipal User user) {
        List<PostResponseDto> myCommentedPosts = postService.getMyCommentedPosts(user);
        return ResponseEntity.ok(Map.of("data", myCommentedPosts));
    }

    @Operation(summary = "내가 스크랩한 글 조회", description = "사용자가 스크랩한 게시물을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "인증 실패", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @GetMapping("/myScrap")
    public ResponseEntity<Map<String, List<PostResponseDto>>> getMyScrappedPosts(@AuthenticationPrincipal User user) {
        List<PostResponseDto> myScrappedPosts = postService.getMyScrappedPosts(user);
        return ResponseEntity.ok(Map.of("data", myScrappedPosts));
    }
}
