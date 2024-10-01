package com.goormthon.everytime.app.repository;

import com.goormthon.everytime.app.domain.board.Board;
import com.goormthon.everytime.app.domain.board.comment.Comment;
import com.goormthon.everytime.app.domain.board.post.Post;
import com.goormthon.everytime.app.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPost(Post post);
    List<Comment> findByPost_BoardAndUser(Board board, User user);
    int countByPost(Post post);
}
