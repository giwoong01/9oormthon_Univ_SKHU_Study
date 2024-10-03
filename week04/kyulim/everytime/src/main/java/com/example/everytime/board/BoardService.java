package com.example.everytime.board;

import com.example.everytime.board.api.dto.BoardListResDto;
import com.example.everytime.board.api.dto.BoardResDto;
import com.example.everytime.board.domain.Board;
import com.example.everytime.board.domain.repository.BoardRepository;
import com.example.everytime.post.api.dto.response.PostResDto;
import com.example.everytime.post.domain.Post;
import com.example.everytime.post.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
    public final BoardRepository boardRepository;

    public BoardListResDto getAllBoards() {
        List<Board> boards = boardRepository.findAll();

        // BoardDto와 PostResDto를 record로 생성
        List<BoardListResDto.BoardDto> boardDtos = boards.stream()
                .map(board -> {
                    List<PostResDto> postDtos = board.getPosts().stream()
                            .map(post -> new PostResDto(
                                    post.getPostId(),
                                    post.getTitle(),
                                    post.getContent(),
                                    post.getAuthor(),
                                    post.getVotes(),
                                    post.getComments()
                            ))
                            .collect(Collectors.toList());

                    return new BoardListResDto.BoardDto(board.getBoardId(), board.getBoardName(), postDtos);
                })
                .collect(Collectors.toList());

        return new BoardListResDto(boardDtos);
    }

    public BoardResDto getBoardById(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시판입니다."));

        List<PostResDto> postResDtos = board.getPosts().stream()
                .map(post -> new PostResDto(
                        post.getPostId(),
                        post.getTitle(),
                        post.getContent(),
                        post.isAnonym() ? "익명" : post.getAuthor(),
                        post.getVotes(),
                        post.getComments()
                )).collect(Collectors.toList());

        return new BoardResDto(board.getBoardId(), board.getBoardName(), postResDtos);
    }
}
