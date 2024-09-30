package com.goormthon.everytime.app.controller.board;

import com.goormthon.everytime.app.dto.board.resDto.BoardResDto;
import com.goormthon.everytime.app.service.board.BoardService;
import com.goormthon.everytime.global.template.ApiResTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "게시판", description = "게시판을 담당하는 api 그룹")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/boards")
    @Operation(
            summary = "전체 게시판 조회",
            description = "전체 게시판과 게시글 정보를 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "전체 게시판 조회 성공"),
                    @ApiResponse(responseCode = "403", description = "권한 문제"),
                    @ApiResponse(responseCode = "404", description = "게시판 정보를 찾을 수 없음"),
                    @ApiResponse(responseCode = "500", description = "서버 문제 or 관리자 문의")
            })
    public ResponseEntity<ApiResTemplate<List<BoardResDto>>> getAllBoards() {
        ApiResTemplate<List<BoardResDto>> data = boardService.getAllBoards();
        return ResponseEntity.status(data.getStatusCode()).body(data);
    }
}
