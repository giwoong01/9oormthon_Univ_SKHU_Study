package com.example.goormthonlogin.member.application;

import com.example.goormthonlogin.auth.error.CustomAuthenticationException;
import com.example.goormthonlogin.auth.jwt.TokenProvider;
import com.example.goormthonlogin.auth.dto.Token;
import com.example.goormthonlogin.member.api.dto.MemberLoginReqDto;
import com.example.goormthonlogin.member.api.dto.MemberLoginResDto;
import com.example.goormthonlogin.member.api.dto.MemberSaveReqDto;
import com.example.goormthonlogin.member.domain.Member;
import com.example.goormthonlogin.member.domain.Role;
import com.example.goormthonlogin.member.domain.repository.MemberRepository;
import com.example.goormthonlogin.member.exeption.InvalidMemberException;
import com.example.goormthonlogin.member.exeption.NotFoundMemberException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder,
                                 TokenProvider tokenProvider) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }
    // 회원가입
    @Transactional
    public void join(MemberSaveReqDto memberSaveReqDto) {
        // 존재하는 이메일인지
        if (memberRepository.existsByEmail(memberSaveReqDto.email())) {
            throw new CustomAuthenticationException("이미 존재하는 이메일입니다.");
        }
        Member member = Member.builder()
                .email(memberSaveReqDto.email())
                .pwd(passwordEncoder.encode(memberSaveReqDto.pwd()))
                .nickname(memberSaveReqDto.nickname())
                .role(Role.ROLE_USER)
                .build();
        memberRepository.save(member);
    }
    // 로그인 -> 로그인성공과 동시에 토큰을 발급 받는다.
    public MemberLoginResDto login(MemberLoginReqDto memberLoginReqDto) {
        Member member = memberRepository.findByEmail(memberLoginReqDto.email())
                .orElseThrow(NotFoundMemberException::new);

        // 비밀번호 확인
        if (!passwordEncoder.matches(memberLoginReqDto.pwd(), member.getPwd())) {
            throw new InvalidMemberException("패스워드가 일치하지 않습니다.");
        }

        // JWT 토큰 생성
        Token token = tokenProvider.createToken(member);

        // MemberLoginResDto 반환
        return MemberLoginResDto.of(member, token.getAccessToken());
    }
}
