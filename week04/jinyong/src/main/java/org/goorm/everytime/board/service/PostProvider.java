package org.goorm.everytime.board.service;

import lombok.RequiredArgsConstructor;
import org.goorm.everytime.board.api.dto.AllBoardDto;
import org.goorm.everytime.board.api.dto.MyPostsDto;
import org.goorm.everytime.board.domain.Post;
import org.goorm.everytime.board.domain.repository.BoardsRespository;
import org.goorm.everytime.board.domain.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostProvider {

    private final BoardsRespository boardsRespository;
    private final PostRepository postRepository;

    //개별 게시판 조회
    public AllBoardDto providePostsOfBoard(Long boardId) {
        return new AllBoardDto(List.of(boardsRespository.findBoardNameByBoardId(boardId)));
    }

    //전체 게시판 조회
    public AllBoardDto provideAllBoard() {
        return boardsRespository.findAllBoard();
    }

    //내가 쓴 글 조회
    public MyPostsDto provideMyPosts(Principal principal) {
        List<Post> memberWrittenPosts = postRepository.findByMember_Username(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("사용자의 글이 존재하지 않습니다."));
        return new MyPostsDto(memberWrittenPosts);
    }
}
