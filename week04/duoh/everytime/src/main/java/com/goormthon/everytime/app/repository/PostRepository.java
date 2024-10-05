package com.goormthon.everytime.app.repository;

import com.goormthon.everytime.app.domain.board.Board;
import com.goormthon.everytime.app.domain.board.post.Post;
import com.goormthon.everytime.app.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findByBoard(Board board, Pageable pageable);
    Optional<Post> findByPostIdAndBoard(Long postId, Board board);
    List<Post> findByBoardAndUser(Board board, User user);
}
