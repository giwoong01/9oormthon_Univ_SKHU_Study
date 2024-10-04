package com.example.everytime.board.domain;

import com.example.everytime.board.domain.repository.BoardRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner init(BoardRepository boardRepository) {
        return args -> {
            boardRepository.save(new Board(null, "자유게시판", BoardType.FREE_BOARD, null));
            boardRepository.save(new Board(null, "비밀게시판", BoardType.SECRET_BOARD, null));
            boardRepository.save(new Board(null, "졸업생게시판", BoardType.GRADUATE_BOARD, null));
            boardRepository.save(new Board(null, "새내기게시판", BoardType.FRESHER_BOARD, null));
            boardRepository.save(new Board(null, "시사•이슈", BoardType.ISSUE_BOARD, null));
            boardRepository.save(new Board(null, "정보게시판", BoardType.INFO_BOARD, null));
            boardRepository.save(new Board(null, "취업•진로", BoardType.CAREER_BOARD, null));
            boardRepository.save(new Board(null, "홍보게시판", BoardType.PROMOTION_BOARD, null));
            boardRepository.save(new Board(null, "동아리•학회", BoardType.CLUB_BOARD, null));
            boardRepository.save(new Board(null, "미디어센터", BoardType.MEDIA_CENTER, null));
            boardRepository.save(new Board(null, "기숙사게시판", BoardType.DORM_BOARD, null));
        };
    }
}