package org.skhu.everytime.community.board.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.skhu.everytime.community.board.api.dto.BoardResponseDto;
import org.skhu.everytime.community.board.application.BoardService;
import org.skhu.everytime.community.board.application.PostService;
import org.skhu.everytime.community.board.domain.Board;
import org.skhu.everytime.community.board.domain.repository.BoardRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final BoardRepository boardRepository;
    private final PostService postService;


    @Operation(summary = "전체 게시판 조회", description = "모든 게시판을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping("/boards")
    public ResponseEntity<Map<String, List<BoardResponseDto>>> getAllBoards() {
        List<BoardResponseDto> boards = boardService.getAllBoards();
        return ResponseEntity.ok(Map.of("boards", boards));
    }

    @Operation(summary = "개별 게시판 조회", description = "특정 게시판을 ID로 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "게시판을 찾을 수 없습니다.", content = @Content(schema = @Schema(example = "게시판을 찾을 수 없습니다.")))
    })
    @GetMapping("/boards/{boardId}")
    public ResponseEntity<BoardResponseDto> getBoard(@PathVariable Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시판을 찾을 수 없습니다."));
        BoardResponseDto response = postService.getPostsByBoard(board);
        return ResponseEntity.ok(response);
    }
}
