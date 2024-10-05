package com.goormthon.everytime.app.service.board;

import com.goormthon.everytime.app.domain.board.Board;
import com.goormthon.everytime.app.domain.board.BoardName;
import com.goormthon.everytime.app.domain.board.post.Post;
import com.goormthon.everytime.app.domain.user.User;
import com.goormthon.everytime.app.dto.board.resDto.BoardDetailResDto;
import com.goormthon.everytime.app.dto.board.resDto.BoardDetailWrapperResDto;
import com.goormthon.everytime.app.dto.board.resDto.PostSummaryResDto;
import com.goormthon.everytime.app.repository.BoardRepository;
import com.goormthon.everytime.app.repository.CommentRepository;
import com.goormthon.everytime.app.repository.PostRepository;
import com.goormthon.everytime.app.repository.UserRepository;
import com.goormthon.everytime.global.exception.CustomException;
import com.goormthon.everytime.global.exception.code.ErrorCode;
import com.goormthon.everytime.global.exception.code.SuccessCode;
import com.goormthon.everytime.global.template.ApiResTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Collections;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardDetailDisplayService {

    private final BoardRepository boardRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public ApiResTemplate<BoardDetailWrapperResDto> getBoardDetail(
            int boardId, int page, Principal principal) {

        Long userId = Long.parseLong(principal.getName());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND, ErrorCode.USER_NOT_FOUND.getMessage()));

        BoardName boardName = BoardName.fromId(boardId);
        Board board = boardRepository.findByBoardNameAndUniversity(boardName, user.getUniversity())
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND, ErrorCode.BOARD_NOT_FOUND.getMessage()));

        Page<Post> postPage = postRepository.findByBoard(board, PageRequest.of(page - 1, 10));

        Page<PostSummaryResDto> postSummaries = postPage.map(post -> {
            int commentCount = commentRepository.countByPost(post);
            return PostSummaryResDto.of(post, commentCount);
        });

        BoardDetailResDto boardDetail = BoardDetailResDto.of(board, postSummaries);

        return ApiResTemplate.success(SuccessCode.GET_BOARD_DETAIL_SUCCESS, BoardDetailWrapperResDto.from(Collections.singletonList(boardDetail)));
    }
}
