package com.goormthon.everytime.app.controller.board;

import com.goormthon.everytime.app.dto.board.reqDto.CommentReqDto;
import com.goormthon.everytime.app.dto.board.reqDto.PostReqDto;
import com.goormthon.everytime.app.dto.board.resDto.PostDetailResDto;
import com.goormthon.everytime.app.service.board.PostService;
import com.goormthon.everytime.global.template.ApiResTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@Tag(name = "게시글", description = "게시글을 담당하는 api 그룹")
public class PostController {

    private final PostService postService;

    @GetMapping("/posts")
    @Operation(
            summary = "게시글 조회",
            description = "게시글을 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "게시글 조회 성공"),
                    @ApiResponse(responseCode = "403", description = "권한 문제"),
                    @ApiResponse(responseCode = "404", description = "게시글 정보를 찾을 수 없음"),
                    @ApiResponse(responseCode = "500", description = "서버 문제 or 관리자 문의")
            }
    )
    public ResponseEntity<ApiResTemplate<PostDetailResDto>> getPost(
            @RequestParam int boardId,
            @RequestParam Long postId,
            Principal principal) {
        ApiResTemplate<PostDetailResDto> data = postService.getPost(boardId, postId, principal);
        return ResponseEntity.status(data.getStatusCode()).body(data);
    }

    @PostMapping("/boards/{boardId}/upload")
    @Operation(
            summary = "게시글 등록",
            description = "사용자가 게시글을 등록합니다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "게시글 등록 성공"),
                    @ApiResponse(responseCode = "403", description = "권한 문제"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "500", description = "서버 문제 or 관리자 문의")
            })
    public ResponseEntity<ApiResTemplate<Void>> createPost(
            @PathVariable int boardId,
            @ModelAttribute @Valid PostReqDto reqDto,
            Principal principal) {
        ApiResTemplate<Void> data = postService.createPost(reqDto, principal, boardId);
        return ResponseEntity.status(data.getStatusCode()).body(data);
    }

    @PostMapping("/boards/{boardId}/{postId}/comment")
    @Operation(
            summary = "댓글 등록",
            description = "게시글에 댓글을 등록합니다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "댓글 등록 성공"),
                    @ApiResponse(responseCode = "403", description = "권한 문제"),
                    @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "500", description = "서버 문제 or 관리자 문의")
            })
    public ResponseEntity<ApiResTemplate<Void>> createComment(
            @PathVariable int boardId,
            @PathVariable Long postId,
            @RequestBody @Valid CommentReqDto reqDto,
            Principal principal) {
        ApiResTemplate<Void> data = postService.createComment(reqDto, boardId, postId, principal);
        return ResponseEntity.status(data.getStatusCode()).body(data);
    }
}
