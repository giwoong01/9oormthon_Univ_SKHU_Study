package org.goorm.everytime.member.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Column(name = "email")
    private String email; //이메일

    @Column(name = "year")
    private int year; //나이

    @Column(name = "university_name")
    private String universityName; //대학교 이름

    @Column(name = "name")
    private String name; //사용자 이름
}
