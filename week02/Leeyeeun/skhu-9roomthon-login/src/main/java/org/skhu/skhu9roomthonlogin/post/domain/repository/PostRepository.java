package org.skhu.skhu9roomthonlogin.post.domain.repository;


import org.skhu.skhu9roomthonlogin.member.domain.Member;
import org.skhu.skhu9roomthonlogin.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByMember(Member member);
}
