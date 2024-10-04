package org.skhu.everytime.community.board.domain.repository;

import org.skhu.everytime.community.board.domain.Board;
import org.skhu.everytime.community.board.domain.Post;
import org.skhu.everytime.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUser(User user);
    List<Post> findByBoard(Board board);
}
