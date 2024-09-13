package com.example.everytime.member.domain;

import com.example.everytime.Post.domain.Post;
import com.example.everytime.School.domain.School;
import com.example.everytime.friend.domain.Friend;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    private int year;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;

    private String name;

    private String nickName;

    private String email;

    private String id;

    private String password;

    private String checkPassword;


    private String department;

    private String profileImg;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @OneToMany(mappedBy = "member")
    private List<Friend> friends = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();

}
