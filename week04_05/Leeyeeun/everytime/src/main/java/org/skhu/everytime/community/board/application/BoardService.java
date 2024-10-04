package org.skhu.everytime.community.board.application;

import lombok.RequiredArgsConstructor;
import org.skhu.everytime.community.board.api.dto.response.BoardResponseDto;
import org.skhu.everytime.community.board.domain.Board;
import org.skhu.everytime.community.board.domain.repository.BoardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    // 전체 게시판 조회
    public List<BoardResponseDto> getAllBoards() {
        return boardRepository.findAll().stream()
                .map(this::boardResponseDto)
                .collect(Collectors.toList());
    }


    // 개별 게시판 조회
    private BoardResponseDto boardResponseDto(Board board) {
        List<BoardResponseDto.ArticleResponseDto> articles = board.getPosts().stream()
                .map(post -> new BoardResponseDto.ArticleResponseDto(
                        post.getPostId(),
                        post.getTitle(),
                        post.getContent(),
                        post.getUser().getNickname(),
                        post.getRecommendCount(),
                        post.getCommentsCount()))
                .collect(Collectors.toList());

        return new BoardResponseDto(
                board.getBoardId(),
                board.getBoardType().getDisplayName(),
                articles);
    }
}
