package org.goorm.everytime.member.repository;


import org.goorm.everytime.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByUsername(String username);
    Optional<Member> findByUsername(String username);
    Optional<Member> findByEmail(String email);
}
