package com.example.loginstudy.member.domain.repository;

import com.example.loginstudy.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    boolean existsByNickname(String nickname);
}
