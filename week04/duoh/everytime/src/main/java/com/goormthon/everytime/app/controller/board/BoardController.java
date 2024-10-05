package com.goormthon.everytime.app.controller.board;

import com.goormthon.everytime.app.dto.board.resDto.BoardDetailWrapperResDto;
import com.goormthon.everytime.app.dto.board.resDto.BoardListWrapperResDto;
import com.goormthon.everytime.app.service.board.BoardDetailDisplayService;
import com.goormthon.everytime.app.service.board.BoardListDisplayService;
import com.goormthon.everytime.global.template.ApiResTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
@Tag(name = "게시판", description = "게시판을 담당하는 api 그룹")
public class BoardController {

    private final BoardListDisplayService boardListDisplayService;
    private final BoardDetailDisplayService boardDetailDisplayService;

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
    public ResponseEntity<ApiResTemplate<BoardListWrapperResDto>> getAllBoards(Principal principal) {
        ApiResTemplate<BoardListWrapperResDto> data = boardListDisplayService.getBoardList(principal);
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
    public ResponseEntity<ApiResTemplate<BoardDetailWrapperResDto>> getSingleBoard(
            @PathVariable int boardId,
            @RequestParam int page,
            Principal principal) {
        ApiResTemplate<BoardDetailWrapperResDto> data = boardDetailDisplayService.getBoardDetail(boardId, page, principal);
        return ResponseEntity.status(data.getStatusCode()).body(data);
    }
}
