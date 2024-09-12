package com.example.goormthonloginstudy.auth.auth.application;

import com.example.goormthonloginstudy.auth.auth.api.dto.request.JoinReqDto;
import com.example.goormthonloginstudy.auth.auth.api.dto.request.LoginReqDto;
import com.example.goormthonloginstudy.auth.auth.api.dto.response.MemberLoginResDto;
import com.example.goormthonloginstudy.auth.auth.api.dto.response.UserInfo;
import com.example.goormthonloginstudy.auth.auth.exception.EmailNotFoundException;
import com.example.goormthonloginstudy.auth.auth.exception.ExistsMemberEmailException;
import com.example.goormthonloginstudy.auth.auth.exception.InvalidPasswordException;
import com.example.goormthonloginstudy.auth.auth.exception.MemberNotFoundException;
import com.example.goormthonloginstudy.global.entity.Status;
import com.example.goormthonloginstudy.member.domain.Member;
import com.example.goormthonloginstudy.member.domain.Role;
import com.example.goormthonloginstudy.member.domain.SocialType;
import com.example.goormthonloginstudy.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthMemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    @Transactional
    public MemberLoginResDto saveUserInfo(UserInfo userInfo, SocialType provider) {
        validateNotFoundEmail(userInfo.email());

        Member member = getExistingMemberOrCreateNew(userInfo, provider);

        validateSocialType(member, provider);

        return MemberLoginResDto.from(member);
    }

    private void validateNotFoundEmail(String email) {
        if (email == null) {
            throw new EmailNotFoundException();
        }
    }

    private Member getExistingMemberOrCreateNew(UserInfo userInfo, SocialType provider) {
        return memberRepository.findByEmail(userInfo.email()).orElseGet(() -> createMember(userInfo, provider));
    }

    private Member createMember(UserInfo userInfo, SocialType provider) {
        String userPicture = getUserPicture(userInfo.picture());
        String name = unionName(userInfo.name(), userInfo.nickname());
        String nickname = getOrSetNickname(userInfo.name(), userInfo.nickname());

        return memberRepository.save(
                Member.builder()
                        .status(Status.ACTIVE)
                        .email(userInfo.email())
                        .name(name)
                        .picture(userPicture)
                        .socialType(provider)
                        .role(Role.ROLE_USER)
                        .nickname(nickname)
                        .build()
        );
    }

    private String unionName(String name, String nickname) {
        return nickname != null ? nickname : name;
    }

    private String getOrSetNickname(String name, String nickname) {
        return (nickname == null || nickname.isEmpty()) ? name : nickname;
    }

    private String getUserPicture(String picture) {
        return Optional.ofNullable(picture)
                .map(this::convertToHighRes)
                .orElseThrow();
    }

    private String convertToHighRes(String url) {
        return url.replace("s96-c", "s2048-c");
    }

    private void validateSocialType(Member member, SocialType provider) {
        if (!provider.equals(member.getSocialType())) {
            throw new ExistsMemberEmailException();
        }
    }

    @Transactional
    public void join(SocialType provider, JoinReqDto joinReqDto) {
        Member member = Member.builder()
                .status(Status.ACTIVE)
                .role(Role.ROLE_USER)
                .email(joinReqDto.email())
                .pwd(encoder.encode(joinReqDto.pwd()))
                .name(joinReqDto.name())
                .nickname(joinReqDto.nickname())
                .socialType(provider)
                .build();

        memberRepository.save(member);
    }

    public MemberLoginResDto login(SocialType provider, LoginReqDto loginReqDto) {
        Member member = memberRepository.findByEmail(loginReqDto.email())
                .orElseThrow(MemberNotFoundException::new);

        validateSocialType(member, provider);
        validationPassword(loginReqDto.pwd(), member.getPwd());

        return MemberLoginResDto.from(member);
    }

    private void validationPassword(String rawPassword, String encodedPassword) {
        if (!encoder.matches(rawPassword, encodedPassword)) {
            throw new InvalidPasswordException();
        }
    }
}
