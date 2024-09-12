package com.example.goormthonloginstudy.member.domain.repository;



import com.example.goormthonloginstudy.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends
        JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
}
