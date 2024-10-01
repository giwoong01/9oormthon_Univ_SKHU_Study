package com.goormthon.everytime.app.repository;

import com.goormthon.everytime.app.domain.board.Board;
import com.goormthon.everytime.app.domain.board.BoardName;
import com.goormthon.everytime.app.domain.user.University;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    boolean existsByBoardNameAndUniversity(BoardName boardName, University university);
    List<Board> findAllByUniversity(University university);
    Optional<Board> findByBoardNameAndUniversity(BoardName boardName, University university);
}
