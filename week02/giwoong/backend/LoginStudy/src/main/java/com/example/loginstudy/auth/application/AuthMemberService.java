package com.example.loginstudy.auth.application;

import com.example.loginstudy.auth.api.dto.request.JoinReqDto;
import com.example.loginstudy.auth.api.dto.request.LoginReqDto;
import com.example.loginstudy.auth.api.dto.response.MemberLoginResDto;
import com.example.loginstudy.auth.api.dto.response.UserInfo;
import com.example.loginstudy.auth.exception.ExistsMemberEmailException;
import com.example.loginstudy.member.domain.Member;
import com.example.loginstudy.member.domain.Role;
import com.example.loginstudy.member.domain.SocialType;
import com.example.loginstudy.member.domain.repository.MemberRepository;
import com.example.loginstudy.member.exception.InvalidMemberException;
import com.example.loginstudy.member.exception.MemberNotFoundException;
import java.util.Optional;
import java.util.UUID;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AuthMemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    public AuthMemberService(MemberRepository memberRepository, PasswordEncoder encoder) {
        this.memberRepository = memberRepository;
        this.encoder = encoder;
    }

    @Transactional
    public MemberLoginResDto saveUserInfo(UserInfo userInfo, SocialType provider) {
        Member member = getExistingMemberOrCreateNew(userInfo, provider);

        validateSocialType(member, provider);

        return MemberLoginResDto.from(member);
    }

    private Member getExistingMemberOrCreateNew(UserInfo userInfo, SocialType provider) {
        return memberRepository.findByEmail(userInfo.email()).orElseGet(() -> createMember(userInfo, provider));
    }

    private Member createMember(UserInfo userInfo, SocialType provider) {
        String userPicture = getUserPicture(userInfo.picture());

        return memberRepository.save(
                Member.builder()
                        .email(userInfo.email())
                        .name(userInfo.name())
                        .picture(userPicture)
                        .socialType(provider)
                        .role(Role.ROLE_USER)
                        .build()
        );
    }

    private String getUserPicture(String picture) {
        return Optional.ofNullable(picture)
                .map(this::convertToHighRes)
                .orElseThrow(NullPointerException::new);
    }

    private String convertToHighRes(String url) {
        return url.replace("s96-c", "s2048-c");
    }

    @Transactional
    public void join(SocialType provider, JoinReqDto joinReqDto) {
        Member member = Member.builder()
                .email(joinReqDto.email())
                .pwd(encoder.encode(joinReqDto.pwd()))
                .nickname(UUID.randomUUID().toString().substring(0, 6))
                .socialType(provider)
                .role(Role.ROLE_USER)
                .build();

        memberRepository.save(member);
    }

    public MemberLoginResDto login(SocialType provider, LoginReqDto loginReqDto) {
        Member member = memberRepository.findByEmail(loginReqDto.email()).orElseThrow(MemberNotFoundException::new);

        validatePassword(loginReqDto.pwd(), member.getPwd());
        validateSocialType(member, provider);

        return MemberLoginResDto.from(member);
    }

    private void validatePassword(CharSequence loginPwd, String memberPwd) {
        if (!encoder.matches(loginPwd, memberPwd)) {
            throw new InvalidMemberException("패스워드가 일치하지 않습니다.");
        }
    }

    private void validateSocialType(Member member, SocialType provider) {
        if (!provider.equals(member.getSocialType())) {
            throw new ExistsMemberEmailException();
        }
    }

}
