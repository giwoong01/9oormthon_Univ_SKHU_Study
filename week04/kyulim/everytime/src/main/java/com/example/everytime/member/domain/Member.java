package com.example.everytime.member.domain;

import com.example.everytime.global.entity.BaseEntity;
import com.example.everytime.post.domain.Post;
import com.example.everytime.friend.domain.Friend;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    private int year;

    @Enumerated(EnumType.STRING)
    private UniversityName universityName;

    private String name;

    private String nickName;

    private String email;

    private String id;

    private String password;

    private String checkPassword;


    private String department;

    private String profileImg;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @OneToMany(mappedBy = "member")
    private List<Friend> friends = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();

    @Builder
    private Member(int year, UniversityName universityName, String name, String nickName, String email, String id, String password, String checkPassword, String department, String profileImg, Role role, SocialType socialType, List<Friend> friends, List<Post> posts) {
        this.year = year;
        this.universityName = universityName;
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.id = id;
        this.password = password;
        this.checkPassword = checkPassword;
        this.department = department;
        this.profileImg = profileImg;
        this.role = role;
        this.socialType = socialType;
        this.friends = friends;
        this.posts = posts;
    }
}
