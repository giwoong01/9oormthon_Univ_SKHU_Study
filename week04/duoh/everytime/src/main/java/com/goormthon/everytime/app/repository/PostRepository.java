package com.goormthon.everytime.app.repository;

import com.goormthon.everytime.app.domain.board.Board;
import com.goormthon.everytime.app.domain.board.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByBoard(Board board);
}
