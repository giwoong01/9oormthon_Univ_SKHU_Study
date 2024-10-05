package org.goorm.everytime.board.service;

import lombok.RequiredArgsConstructor;
import org.goorm.everytime.board.api.dto.posts.AllBoardDto;
import org.goorm.everytime.board.api.dto.comments.MyPostsDto;
import org.goorm.everytime.board.api.dto.posts.BoardDto;
import org.goorm.everytime.board.domain.Boards;
import org.goorm.everytime.board.domain.Post;
import org.goorm.everytime.board.domain.repository.BoardsRespository;
import org.goorm.everytime.board.domain.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostProvider {

    private final BoardsRespository boardsRespository;
    private final PostRepository postRepository;

    //개별 게시판 조회
    @Transactional(readOnly = true)
    public AllBoardDto providePostsOfBoard(Long boardId) {
        List<Boards> boards = boardsRespository.findAllById(boardId);
        List<BoardDto> boardDtos = boards.stream()
                .map(BoardDto::new)
                .collect(Collectors.toList());
        return new AllBoardDto(boardDtos);
    }

    //전체 게시판 조회
    @Transactional(readOnly = true)
    public AllBoardDto provideAllBoard() {
        List<Boards> boards = boardsRespository.findAll();
        List<BoardDto> boardDtos = boards.stream()
                .map(BoardDto::new)
                .collect(Collectors.toList());

        return new AllBoardDto(boardDtos);
    }

    //내가 쓴 글 조회
    @Transactional(readOnly = true)
    public MyPostsDto provideMyPosts(Principal principal) {
        List<Post> memberWrittenPosts = postRepository.findByMember_Username(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("사용자의 글이 존재하지 않습니다."));
        return new MyPostsDto(memberWrittenPosts);
    }
}
