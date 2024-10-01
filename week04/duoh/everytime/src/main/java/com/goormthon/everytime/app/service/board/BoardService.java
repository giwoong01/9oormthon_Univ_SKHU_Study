package com.goormthon.everytime.app.service.board;

import com.goormthon.everytime.app.domain.board.Board;
import com.goormthon.everytime.app.domain.board.BoardName;
import com.goormthon.everytime.app.domain.board.post.Post;
import com.goormthon.everytime.app.domain.user.User;
import com.goormthon.everytime.app.dto.board.resDto.BoardResDto;
import com.goormthon.everytime.app.dto.board.resDto.DetailedPostResDto;
import com.goormthon.everytime.app.dto.board.resDto.SingleBoardResDto;
import com.goormthon.everytime.app.repository.BoardRepository;
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
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public ApiResTemplate<List<BoardResDto>> getAllBoards(Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND, ErrorCode.USER_NOT_FOUND.getMessage()));

        List<Board> boards = boardRepository.findAllByUniversity(user.getUniversity());

        List<BoardResDto> resDto = boards.stream()
                .map(BoardResDto::from)
                .toList();

        return ApiResTemplate.success(SuccessCode.GET_BOARD_LIST_SUCCESS, resDto);
    }

    public ApiResTemplate<SingleBoardResDto> getSingleBoard(int boardId, int page, Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND, ErrorCode.USER_NOT_FOUND.getMessage()));

        BoardName boardName = BoardName.fromId(boardId);
        Board board = boardRepository.findByBoardNameAndUniversity(boardName, user.getUniversity())
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND, ErrorCode.BOARD_NOT_FOUND.getMessage()));

        Page<Post> posts = postRepository.findByBoard(board, PageRequest.of(page - 1, 10));
        Page<DetailedPostResDto> postResDtos = posts.map(DetailedPostResDto::from);

        SingleBoardResDto resDto = SingleBoardResDto.of(board, postResDtos);

        return ApiResTemplate.success(SuccessCode.GET_SINGLE_BOARD_SUCCESS, resDto);
    }
}
