package org.skhu.everytime.community.board.domain.repository;

import org.skhu.everytime.community.board.domain.Comment;
import org.skhu.everytime.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByUser(User user);
}