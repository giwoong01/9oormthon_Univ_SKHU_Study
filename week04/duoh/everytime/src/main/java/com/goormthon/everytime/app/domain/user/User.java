package com.goormthon.everytime.app.domain.user;

import com.goormthon.everytime.app.domain.Image;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false, length = 2)
    private String year;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private University university;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String nickName;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(nullable = false, length = 100, unique = true)
    private String id;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType roleType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "anonym_image_id")
    private Image anonymImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "real_image_id")
    private Image realImage;

    @Builder
    private User(String year, University university, String name, String nickName, String email,
                 String id, String password, RoleType roleType, Image anonymImage, Image realImage) {
        this.year = year;
        this.university = university;
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.id = id;
        this.password = password;
        this.roleType = roleType;
        this.anonymImage = anonymImage;
        this.realImage = realImage;
    }
}
