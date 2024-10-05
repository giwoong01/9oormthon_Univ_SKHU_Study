package com.goormthon.everytime.app.service.board;

import com.goormthon.everytime.app.domain.board.Board;
import com.goormthon.everytime.app.domain.board.BoardName;
import com.goormthon.everytime.app.domain.board.post.Post;
import com.goormthon.everytime.app.domain.user.User;
import com.goormthon.everytime.app.dto.board.resDto.CommentResDto;
import com.goormthon.everytime.app.dto.board.resDto.PostDetailResDto;
import com.goormthon.everytime.app.dto.board.resDto.PostDetailWrapperResDto;
import com.goormthon.everytime.app.repository.*;
import com.goormthon.everytime.global.exception.CustomException;
import com.goormthon.everytime.global.exception.code.ErrorCode;
import com.goormthon.everytime.global.exception.code.SuccessCode;
import com.goormthon.everytime.global.template.ApiResTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostDisplayService {

    private final BoardRepository boardRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public ApiResTemplate<PostDetailWrapperResDto> getPost(
            int boardId, Long postId, Principal principal) {

        Long userId = Long.parseLong(principal.getName());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND, ErrorCode.USER_NOT_FOUND.getMessage()));

        BoardName boardName = BoardName.fromId(boardId);
        Board board = boardRepository.findByBoardNameAndUniversity(boardName, user.getUniversity())
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND, ErrorCode.BOARD_NOT_FOUND.getMessage()));

        Post post = postRepository.findByPostIdAndBoard(postId, board)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));

        int commentsCount = commentRepository.countByPost(post);

        List<CommentResDto> comments = commentRepository.findAllByPost(post).stream()
                .map(CommentResDto::from)
                .toList();

        PostDetailResDto postDetail = PostDetailResDto.of(post, commentsCount, comments);

        return ApiResTemplate.success(SuccessCode.GET_POST_SUCCESS, PostDetailWrapperResDto.from(Collections.singletonList(postDetail)));
    }
}
