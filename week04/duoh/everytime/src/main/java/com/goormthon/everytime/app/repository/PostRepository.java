package com.goormthon.everytime.app.repository;

import com.goormthon.everytime.app.domain.board.Board;
import com.goormthon.everytime.app.domain.board.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findByBoard(Board board, Pageable pageable);
}
