package com.goormthon.everytime.app.service.board;

import com.goormthon.everytime.app.domain.board.Board;
import com.goormthon.everytime.app.domain.board.BoardName;
import com.goormthon.everytime.app.domain.board.comment.Comment;
import com.goormthon.everytime.app.domain.board.post.Post;
import com.goormthon.everytime.app.domain.user.User;
import com.goormthon.everytime.app.dto.board.reqDto.CommentReqDto;
import com.goormthon.everytime.app.repository.*;
import com.goormthon.everytime.global.exception.CustomException;
import com.goormthon.everytime.global.exception.code.ErrorCode;
import com.goormthon.everytime.global.exception.code.SuccessCode;
import com.goormthon.everytime.global.template.ApiResTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class CommentCreateService {

    private final BoardRepository boardRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public ApiResTemplate<Void> createComment(
            CommentReqDto reqDto, int boardId, Long postId, Principal principal) {

        Long userId = Long.parseLong(principal.getName());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND, ErrorCode.USER_NOT_FOUND.getMessage()));

        BoardName boardName = BoardName.fromId(boardId);
        Board board = boardRepository.findByBoardNameAndUniversity(boardName, user.getUniversity())
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND, ErrorCode.BOARD_NOT_FOUND.getMessage()));

        Post post = postRepository.findByPostIdAndBoard(postId, board)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));

        Comment comment = reqDto.toEntity(post, user);
        commentRepository.save(comment);

        return ApiResTemplate.success(SuccessCode.COMMENT_CREATED_SUCCESS, null);
    }
}
