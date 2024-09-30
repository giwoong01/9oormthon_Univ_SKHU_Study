package com.goormthon.everytime.app.repository;

import com.goormthon.everytime.app.domain.board.post.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostImageRepository extends JpaRepository<PostImage, Long> {
}
