package com.example.everytime.Post.domain.repository;

import com.example.everytime.Post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
