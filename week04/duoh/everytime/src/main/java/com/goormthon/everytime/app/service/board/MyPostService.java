package com.goormthon.everytime.app.service.board;

import com.goormthon.everytime.app.domain.board.Board;
import com.goormthon.everytime.app.domain.user.User;
import com.goormthon.everytime.app.dto.board.resDto.MyBoardResDto;
import com.goormthon.everytime.app.dto.board.resDto.MyPostResDto;
import com.goormthon.everytime.app.repository.BoardRepository;
import com.goormthon.everytime.app.repository.CommentRepository;
import com.goormthon.everytime.app.repository.PostRepository;
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
public class MyPostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public ApiResTemplate<List<MyBoardResDto>> getMyPosts(Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND, ErrorCode.USER_NOT_FOUND.getMessage()));

        List<MyBoardResDto> resDtos = boardRepository.findAllByUniversity(user.getUniversity())
                .stream()
                .map(board -> getPostsInBoard(board, user))
                .filter(boardResDto -> !boardResDto.posts().isEmpty())
                .collect(Collectors.toList());

        return ApiResTemplate.success(SuccessCode.GET_MY_POSTS_SUCCESS, resDtos);
    }

    private MyBoardResDto getPostsInBoard(Board board, User user) {
        List<MyPostResDto> resDtos = postRepository.findByBoardAndUser(board, user).stream()
                .map(post -> MyPostResDto.of(post, commentRepository.countByPost(post)))
                .collect(Collectors.toList());

        return MyBoardResDto.of(board, resDtos);
    }
}
