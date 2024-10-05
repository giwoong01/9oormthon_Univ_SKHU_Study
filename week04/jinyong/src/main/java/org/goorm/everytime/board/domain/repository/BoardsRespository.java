package org.goorm.everytime.board.domain.repository;

import org.goorm.everytime.board.api.dto.posts.AllBoardDto;
import org.goorm.everytime.board.api.dto.posts.BoardDto;
import org.goorm.everytime.board.domain.Boards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardsRespository extends JpaRepository<Boards, Long> {
    List<Boards> findAllById(Long boardId);
}
