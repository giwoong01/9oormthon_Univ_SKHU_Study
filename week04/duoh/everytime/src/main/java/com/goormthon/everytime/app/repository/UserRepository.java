package com.goormthon.everytime.app.repository;

import com.goormthon.everytime.app.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
    boolean existsById(String id);
}
