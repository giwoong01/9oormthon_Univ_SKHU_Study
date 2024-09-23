package com.example.everytime.universityName.domain;

import com.example.everytime.board.domain.Board;
import com.example.everytime.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UniversityName {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "university_name_id")
    private Long universityNameId;

    private String name;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    @OneToMany(mappedBy = "universityName")
    private List<Member> members = new ArrayList<>();

    @OneToMany(mappedBy = "universityName")
    private List<Board> boards = new ArrayList<>();

}
