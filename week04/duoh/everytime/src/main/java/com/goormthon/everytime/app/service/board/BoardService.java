package com.goormthon.everytime.app.service.board;

import com.goormthon.everytime.app.domain.board.Board;
import com.goormthon.everytime.app.domain.user.User;
import com.goormthon.everytime.app.dto.board.resDto.BoardResDto;
import com.goormthon.everytime.app.repository.BoardRepository;
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

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
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
}
