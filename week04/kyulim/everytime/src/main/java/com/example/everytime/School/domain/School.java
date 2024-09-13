package com.example.everytime.School.domain;

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
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "school_id")
    private Long schoolId;

    private String name;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    @OneToMany(mappedBy = "school")
    private List<Member> members = new ArrayList<>();

    @OneToMany(mappedBy = "school")
    private List<Board> boards = new ArrayList<>();

}
