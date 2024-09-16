package com.goormthon.everytime.app.domain.user;

import com.goormthon.everytime.app.domain.image.Image;
import com.goormthon.everytime.app.domain.user.university.University;
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

    @Column(name = "year", nullable = false, length = 2)
    private String year;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "nick_name", nullable = false, length = 100)
    private String nickName;

    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;

    @Column(name = "id", nullable = false, length = 100, unique = true)
    private String id;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_type", nullable = false)
    private RoleType roleType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id", nullable = false)
    private University university;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "anonym_image_id")
    private Image anonymImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "real_image_id")
    private Image realImage;

    @Builder
    private User(String year, String name, String nickName, String email,
                 String id, String password, RoleType roleType,
                 University university, Image anonymImage, Image realImage) {
        this.year = year;
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.id = id;
        this.password = password;
        this.roleType = roleType;
        this.university = university;
        this.anonymImage = anonymImage;
        this.realImage = realImage;
    }
}
