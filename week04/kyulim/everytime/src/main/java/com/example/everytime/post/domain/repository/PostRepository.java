package com.example.everytime.post.domain.repository;

import com.example.everytime.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
