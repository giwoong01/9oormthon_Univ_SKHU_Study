package com.example.everytime.School.domain.repository;

import com.example.everytime.School.domain.School;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<School, Long> {
}
