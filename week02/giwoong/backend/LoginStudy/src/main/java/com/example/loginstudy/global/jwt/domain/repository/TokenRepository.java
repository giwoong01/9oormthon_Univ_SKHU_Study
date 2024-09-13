package com.example.loginstudy.global.jwt.domain.repository;

import com.example.loginstudy.global.jwt.domain.Token;
import com.example.loginstudy.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
    boolean existsByMember(Member member);

    Optional<Token> findByMember(Member member);

    boolean existsByRefreshToken(String refreshToken);

    Optional<Token> findByRefreshToken(String refreshToken);
}
