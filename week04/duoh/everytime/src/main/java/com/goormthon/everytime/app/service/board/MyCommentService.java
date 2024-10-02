package com.goormthon.everytime.app.service.board;

import com.goormthon.everytime.app.domain.board.Board;
import com.goormthon.everytime.app.domain.user.User;
import com.goormthon.everytime.app.dto.board.resDto.MyBoardResDto;
import com.goormthon.everytime.app.dto.board.resDto.MyPostResDto;
import com.goormthon.everytime.app.repository.BoardRepository;
import com.goormthon.everytime.app.repository.CommentRepository;
import com.goormthon.everytime.app.repository.UserRepository;
import com.goormthon.everytime.global.exception.CustomException;
import com.goormthon.everytime.global.exception.code.ErrorCode;
import com.goormthon.everytime.global.exception.code.SuccessCode;
import com.goormthon.everytime.global.template.ApiResTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyCommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    public ApiResTemplate<List<MyBoardResDto>> getMyComments(Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND, ErrorCode.USER_NOT_FOUND.getMessage()));

        List<MyBoardResDto> myBoards = boardRepository.findAllByUniversity(user.getUniversity())
                .stream()
                .map(board -> getCommentsInBoard(board, user))
                .filter(boardResDto -> !boardResDto.posts().isEmpty())
                .collect(Collectors.toList());

        return ApiResTemplate.success(SuccessCode.GET_MY_COMMENTS_SUCCESS, myBoards);
    }

    private MyBoardResDto getCommentsInBoard(Board board, User user) {
        List<MyPostResDto> myComments = commentRepository.findByPost_BoardAndUser(board, user).stream()
                .map(comment -> MyPostResDto.of(comment.getPost(), commentRepository.countByPost(comment.getPost())))
                .distinct()
                .collect(Collectors.toList());

        return MyBoardResDto.of(board, myComments);
    }
}
