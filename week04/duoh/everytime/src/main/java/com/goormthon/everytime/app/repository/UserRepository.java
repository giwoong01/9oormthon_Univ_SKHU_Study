package com.goormthon.everytime.app.repository;

import com.goormthon.everytime.app.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(String id);
    Optional<User> findByEmail(String email);
    Optional<User> findByNickName(String nickName);
    boolean existsByEmail(String email);
    boolean existsById(String id);
}
