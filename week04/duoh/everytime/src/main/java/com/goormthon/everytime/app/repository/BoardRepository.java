package com.goormthon.everytime.app.repository;

import com.goormthon.everytime.app.domain.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findAll();
}
