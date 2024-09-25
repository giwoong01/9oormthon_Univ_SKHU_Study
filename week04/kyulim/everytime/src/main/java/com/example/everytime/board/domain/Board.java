package com.example.everytime.board.domain;

import com.example.everytime.global.entity.BaseEntity;
import com.example.everytime.post.domain.Post;
import com.example.everytime.universityName.domain.UniversityName;
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
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long boardId;

    private String title;

    private String description;

    @OneToMany(mappedBy = "board")
    private List<Post> posts = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_name_id")
    private UniversityName universityName;

}
