package org.skhu.everytime.user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
@Table(name = "USERS")
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true) // 이메일은 공백 및 중복 불가
    private String email;

    @Column(nullable = false) // 비밀번호는 공백 불가
    private String password;

    @Column(nullable = false, unique = true) // 닉네임은 공백 및 중복 불가
    private String nickname;

    @Column(nullable = false) // 이름은 공백 불가
    private String name;

    @Column(nullable = false) // 대학명은 공백 불가
    private String universityName;

    @Column(nullable = false) // 입학연도는 공백 불가
    private int year;

    private String socialId; // 소셜 로그인 시 식별자, null 가능
    private String imageUrl; // 프로필 이미지, null 가능

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private SocialType socialType; // 소셜 타입, null 가능

    private String refreshToken; // null 가능

    public void authorizeUser() {
        this.role = Role.USER;
    }

    // 비밀번호 암호화 메소드
    public void passwordEncode(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    public void updateNickname(String updateNickname) {
        this.nickname = updateNickname;
    }

    public void updateYear(int updateYear) {
        this.year = updateYear;
    }

    public void updatePassword(String updatePassword, PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(updatePassword);
    }

    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }
}
