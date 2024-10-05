package com.goormthon.everytime.global.config;

import com.goormthon.everytime.app.domain.board.Board;
import com.goormthon.everytime.app.domain.board.BoardName;
import com.goormthon.everytime.app.domain.user.University;
import com.goormthon.everytime.app.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BoardInitializer implements ApplicationRunner {

    private final BoardRepository boardRepository;

    @Override
    public void run(ApplicationArguments args) {
        init();
    }

    private void init() {
        for (University university : University.values()) {
            for (BoardName boardName : BoardName.values()) {
                if (!boardRepository.existsByBoardNameAndUniversity(boardName, university)) {
                    boardRepository.save(Board.builder()
                            .boardName(boardName)
                            .university(university)
                            .build());
                }
            }
        }
    }
}
