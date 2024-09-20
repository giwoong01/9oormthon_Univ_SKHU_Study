package org.goorm.everytime.board.domain.repository;

import org.goorm.everytime.board.api.dto.AllBoardDto;
import org.goorm.everytime.board.api.dto.BoardDto;
import org.goorm.everytime.board.domain.Boards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardsRespository extends JpaRepository<Boards, Long> {

    @Query("SELECT b.id, b.boardName, b.posts FROM Boards b WHERE b.id = :boardId")
    BoardDto findBoardNameByBoardId(Long boardId);

    @Query("SELECT b.id, b.boardName, b.posts FROM Boards b")
    AllBoardDto findAllBoard();
}
