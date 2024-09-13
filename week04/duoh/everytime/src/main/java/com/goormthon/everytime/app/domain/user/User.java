package com.goormthon.everytime.app.domain.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "year", nullable = false, length = 2)
    private String year;

    @Column(name = "university_name", nullable = false, length = 100)
    private String universityName;

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

    @Builder
    private User(String year, String universityName, String name, String nickName, String email, String id, String password, RoleType roleType) {
        this.year = year;
        this.universityName = universityName;
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.id = id;
        this.password = password;
        this.roleType = roleType;
    }
}
