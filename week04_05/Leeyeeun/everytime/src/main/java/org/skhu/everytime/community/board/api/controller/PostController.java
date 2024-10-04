package org.skhu.everytime.community.board.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.skhu.everytime.community.board.api.dto.request.PostRequestDto;
import org.skhu.everytime.community.board.api.dto.response.PostResponseDto;
import org.skhu.everytime.community.board.application.PostService;
import org.skhu.everytime.community.board.domain.Board;
import org.skhu.everytime.community.board.domain.Post;
import org.skhu.everytime.community.board.domain.repository.BoardRepository;
import org.skhu.everytime.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final BoardRepository boardRepository;

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

    // 게시글 등록 메소드
    @Operation(summary = "게시글 등록", description = "게시판에 새로운 게시글을 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 등록 성공"),
            @ApiResponse(responseCode = "400", description = "게시글 등록 실패", content = @Content(schema = @Schema(example = "제목 또는 내용이 비어있습니다.")))
    })
    @PostMapping("/boards/{boardId}/upload")
    public ResponseEntity<String> uploadPost(
            @PathVariable Long boardId,
            @RequestBody PostRequestDto postRequestDto,
            User user) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시판을 찾을 수 없습니다."));

        Post post = new Post(postRequestDto.title(), postRequestDto.content(), board, user);
        postService.savePost(post); // 게시글 저장 서비스 호출

        return ResponseEntity.ok("게시글이 등록되었습니다.");
    }

}
