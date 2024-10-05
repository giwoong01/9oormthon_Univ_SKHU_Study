package com.example.everytime.post.domain.repository;

import com.example.everytime.post.domain.Comment;
import com.example.everytime.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost_PostId(Long postId);
}
