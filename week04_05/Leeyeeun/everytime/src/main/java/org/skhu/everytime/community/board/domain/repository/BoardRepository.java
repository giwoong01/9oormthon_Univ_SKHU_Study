package org.skhu.everytime.community.board.domain.repository;

import org.skhu.everytime.community.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    // 게시판 이름으로 게시판 찾기
    Optional<Board> findById(Long boardId);

    // 게시판 ID로 게시판 삭제
    void deleteById(Long id);
}
