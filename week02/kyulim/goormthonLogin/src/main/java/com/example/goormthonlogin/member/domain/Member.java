package com.example.goormthonlogin.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String email;

    private String pwd;

    private String name;

    private String nickname;


    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Builder
    public Member(String email, String pwd, String name, String nickname, Role role, SocialType socialType) {
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.nickname = nickname;
        this.role = role;
        this.socialType = socialType;
    }

}
