package org.goorm.everytime.member.domain;

import jakarta.persistence.*;
import lombok.*;
import org.goorm.everytime.auth.domain.SocialType;
import org.goorm.everytime.board.domain.Comment;
import org.goorm.everytime.board.domain.Post;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name")
    private String username; //아이디

    @Column(name = "user_password")
    private String password; //비밀번호

    @Column(name = "user_nickname")
    private String nickname; //닉네임

    @Enumerated(EnumType.STRING)
    private Authority authority;//권한

    @Enumerated(EnumType.STRING)
    private SocialType socialType; //로그인 타입

    private String email; //이메일

    private int year; //나이

    @Column(name = "university_name")
    private String universityName; //대학교 이름

    private String name; //사용자 이름

    /*
    연관관계
     */
    @OneToMany(mappedBy = "member")
    private List<Post> writtenPosts = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> writtenComments = new ArrayList<>();

    @Builder
    public Member(String username, String password, String nickname, Authority authority, SocialType socialType, String email, int year, String universityName, String name) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.authority = authority;
        this.socialType = socialType;
        this.email = email;
        this.year = year;
        this.universityName = universityName;
        this.name = name;
    }


    @Builder
    public Member(String name, SocialType socialType, String email) {
        this.name = name;
        this.socialType = socialType;
        this.email = email;
    }
}
