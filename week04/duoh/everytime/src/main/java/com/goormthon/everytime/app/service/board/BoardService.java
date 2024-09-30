package com.goormthon.everytime.app.service.board;

import com.goormthon.everytime.app.dto.board.resDto.BoardResDto;
import com.goormthon.everytime.app.repository.BoardRepository;
import com.goormthon.everytime.global.exception.code.SuccessCode;
import com.goormthon.everytime.global.template.ApiResTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    public ApiResTemplate<List<BoardResDto>> getAllBoards() {
        List<BoardResDto> resDto = boardRepository.findAll().stream()
                .map(BoardResDto::from)
                .toList();

        return ApiResTemplate.success(SuccessCode.GET_BOARD_LIST_SUCCESS, resDto);
    }
}
