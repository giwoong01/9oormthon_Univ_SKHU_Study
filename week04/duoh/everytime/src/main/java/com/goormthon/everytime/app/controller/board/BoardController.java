package com.goormthon.everytime.app.controller.board;

import com.goormthon.everytime.app.dto.board.reqDto.PostReqDto;
import com.goormthon.everytime.app.dto.board.resDto.BoardResDto;
import com.goormthon.everytime.app.dto.board.resDto.SingleBoardResDto;
import com.goormthon.everytime.app.service.board.BoardService;
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
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
@Tag(name = "게시판", description = "게시판을 담당하는 api 그룹")
public class BoardController {

    private final BoardService boardService;
    private final PostService postService;

    @GetMapping
    @Operation(
            summary = "전체 게시판 조회",
            description = "전체 게시판과 게시글 정보를 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "전체 게시판 조회 성공"),
                    @ApiResponse(responseCode = "403", description = "권한 문제"),
                    @ApiResponse(responseCode = "404", description = "게시판 정보를 찾을 수 없음"),
                    @ApiResponse(responseCode = "500", description = "서버 문제 or 관리자 문의")
            })
    public ResponseEntity<ApiResTemplate<List<BoardResDto>>> getAllBoards(Principal principal) {
        ApiResTemplate<List<BoardResDto>> data = boardService.getAllBoards(principal);
        return ResponseEntity.status(data.getStatusCode()).body(data);
    }

    @GetMapping("/{boardId}")
    @Operation(
            summary = "개별 게시판 조회",
            description = "특정 게시판과 해당 게시글 정보를 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "개별 게시판 조회 성공"),
                    @ApiResponse(responseCode = "403", description = "권한 문제"),
                    @ApiResponse(responseCode = "404", description = "게시판 정보를 찾을 수 없음"),
                    @ApiResponse(responseCode = "500", description = "서버 문제 or 관리자 문의")
            }
    )
    public ResponseEntity<ApiResTemplate<SingleBoardResDto>> getSingleBoard(
            @PathVariable int boardId,
            @RequestParam int page,
            Principal principal) {
        ApiResTemplate<SingleBoardResDto> data = boardService.getSingleBoard(boardId, page, principal);
        return ResponseEntity.status(data.getStatusCode()).body(data);
    }

    @PostMapping("/{boardId}/upload")
    @Operation(
            summary = "게시글 등록",
            description = "사용자가 게시글을 등록합니다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "게시글 등록 성공"),
                    @ApiResponse(responseCode = "403", description = "권한 문제"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "500", description = "서버 문제 or 관리자 문의")
            })
    public ResponseEntity<ApiResTemplate<Void>> uploadPost(
            @PathVariable int boardId,
            @ModelAttribute @Valid PostReqDto reqDto,
            Principal principal) {
        ApiResTemplate<Void> data = postService.uploadPost(reqDto, principal, boardId);
        return ResponseEntity.status(data.getStatusCode()).body(data);
    }
}
