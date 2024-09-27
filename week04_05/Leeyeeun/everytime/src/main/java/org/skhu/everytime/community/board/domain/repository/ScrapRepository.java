package org.skhu.everytime.community.board.domain.repository;

import org.skhu.everytime.community.board.domain.Scrap;
import org.skhu.everytime.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    List<Scrap> findByUser(User user);
}