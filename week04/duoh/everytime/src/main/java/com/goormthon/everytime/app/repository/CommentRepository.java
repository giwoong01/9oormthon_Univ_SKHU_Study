package com.goormthon.everytime.app.repository;

import com.goormthon.everytime.app.domain.board.comment.Comment;
import com.goormthon.everytime.app.domain.board.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPost(Post post);
    int countByPost(Post post);
}
