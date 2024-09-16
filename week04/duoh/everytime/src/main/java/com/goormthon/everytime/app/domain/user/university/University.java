package com.goormthon.everytime.app.domain.user.university;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class University {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "university_id")
    private Long universityId;

    @Enumerated(EnumType.STRING)
    @Column(name = "university_name", nullable = false, length = 100)
    private UniversityName universityName;


    @Builder
    private University(UniversityName universityName) {
        this.universityName = universityName;
    }
}
