package com.example.everytime.member.application;

import com.example.everytime.auth.dto.TokenDto;
import com.example.everytime.auth.jwt.TokenProvider;
import com.example.everytime.auth.jwt.exception.InvalidMemberException;
import com.example.everytime.auth.jwt.exception.NotFoundMemberException;
import com.example.everytime.member.api.dto.reqeust.MemberJoinReqDto;
import com.example.everytime.member.api.dto.reqeust.MemberLoginReqDto;
import com.example.everytime.member.api.dto.response.MemberInfoResDto;
import com.example.everytime.member.api.dto.response.MemberLoginResDto;
import com.example.everytime.member.domain.Member;
import com.example.everytime.member.domain.Role;
import com.example.everytime.member.domain.repository.MemberRepository;
import com.example.everytime.member.domain.UniversityName;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Transactional
    public void join(MemberJoinReqDto memberJoinReqDto) {
        validateEmailUniqueness(memberJoinReqDto.email());
        validatePasswordMatch(memberJoinReqDto.password(), memberJoinReqDto.checkPassword());

        Member member = Member.builder()
                .year(memberJoinReqDto.year())
                .universityName(UniversityName.valueOf(memberJoinReqDto.universityName()))
                .name(memberJoinReqDto.name())
                .nickName(memberJoinReqDto.nickName())
                .email(memberJoinReqDto.email())
                .id(memberJoinReqDto.id())
                .password(passwordEncoder.encode(memberJoinReqDto.password()))
                .checkPassword(memberJoinReqDto.checkPassword())
                .role(Role.ROLE_USER)
                .build();
        memberRepository.save(member);
    }

    public MemberLoginResDto login(MemberLoginReqDto memberLoginReqDto) {
        Member member = memberRepository.findById(memberLoginReqDto.id()).orElseThrow(NotFoundMemberException::new);
        TokenDto token = tokenProvider.generateToken(member.getId());
        if (!passwordEncoder.matches(memberLoginReqDto.password(), member.getPassword())) {
            throw new InvalidMemberException("패스워드가 일치하지 않습니다.");
        }
        return new MemberLoginResDto(token.accessToken(), token.refreshToken());
    }

    public MemberInfoResDto getMemberInfo(Principal principal) {
        Long memberId = Long.parseLong(principal.getName());
        Member member = memberRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);

        return MemberInfoResDto.from(member);
    }

    private void validateEmailUniqueness(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new InvalidMemberException("이미 존재하는 이메일입니다.");
        }
    }

    private void validatePasswordMatch(String password, String checkPassword) {
        if (!password.equals(checkPassword)) {
            throw new IllegalArgumentException("비밀번호를 다시 확인해주세요");
        }
    }
}
