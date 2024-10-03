package com.example.everytime.board.api;

import com.example.everytime.board.BoardService;
import com.example.everytime.board.api.dto.BoardListResDto;
import com.example.everytime.board.api.dto.BoardResDto;
import com.example.everytime.global.template.RspTemplate;
import com.example.everytime.post.api.dto.response.PostResDto;
import com.example.everytime.post.applicatioin.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @Operation(summary = "전체 게시판 조회", description = "전체 게시판 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "전체 게시판 조회 성공 !"),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
    })
    @GetMapping()
    public RspTemplate<BoardListResDto> getAllBoards() {
        BoardListResDto boardList = boardService.getAllBoards();
        return new RspTemplate<>(HttpStatus.OK, "전체 게시판 조회 성공", boardList);
    }

    @Operation(summary = "개별 게시판 조회", description = "개별 게시판 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "개별 게시판 조회 성공 !"),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
    })
    @GetMapping("/{boardId}")
    public RspTemplate<BoardResDto> getBoardById(@PathVariable Long boardId) {
        BoardResDto boardResDto = boardService.getBoardById(boardId);
        return new RspTemplate<>(HttpStatus.OK, "개별 게시판 조회 성공", boardResDto);
    }
}
