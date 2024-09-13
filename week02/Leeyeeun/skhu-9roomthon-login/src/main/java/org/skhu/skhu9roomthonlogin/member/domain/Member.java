package org.skhu.skhu9roomthonlogin.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.skhu.skhu9roomthonlogin.member.exception.InvalidMemberException;
import org.skhu.skhu9roomthonlogin.member.exception.InvalidNickNameAddressException;
import org.skhu.skhu9roomthonlogin.post.domain.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    private static final Pattern NICKNAME_PATTERN = Pattern.compile("^[a-z A-Z0-9가-힣]*$");
    private static final int MAX_NICKNAME_LENGTH = 8;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    private String email;
    private String pwd;
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval=true)
    private List<Post> posts = new ArrayList<>();

    @Builder
    private Member(String email, String pwd, String nickname, Role role) {
        validateNickname(nickname);
        this.email = email;
        this.pwd = pwd;
        this.nickname = nickname;
        this.role = role;
    }

    private void validateNickname(String nickname) {
        Matcher matcher = NICKNAME_PATTERN.matcher(nickname);

        if (!matcher.matches()) {
            throw new InvalidNickNameAddressException();
        }
        if (nickname.isEmpty() || nickname.length() >= MAX_NICKNAME_LENGTH) {
            throw new InvalidMemberException(String.format(" 닉네임은 1자 이상 %d자 이하여야 합니다.", MAX_NICKNAME_LENGTH));
        }
    }
}