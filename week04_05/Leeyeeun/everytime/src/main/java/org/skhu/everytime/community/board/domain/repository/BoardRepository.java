package org.skhu.everytime.community.board.domain.repository;

import org.skhu.everytime.community.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
