package com.example.goormthonloginstudy.member.domain;

import com.example.goormthonloginstudy.global.entity.BaseEntity;
import com.example.goormthonloginstudy.global.entity.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String email;

    private String pwd;

    private String name;

    private String picture;

    @Enumerated(value = EnumType.STRING)
    private SocialType socialType;

    private String nickname;

    @Builder
    private Member(Status status,
                   Role role,
                   String email,
                   String pwd,
                   String name,
                   String picture,
                   SocialType socialType,
                   String nickname) {
        this.status = status;
        this.role = role;
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.picture = picture;
        this.socialType = socialType;
        this.nickname = nickname;
    }
}
