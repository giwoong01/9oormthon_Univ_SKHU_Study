package org.skhu.skhu9roomthonlogin.member.application;
import org.skhu.skhu9roomthonlogin.global.jwt.TokenProvider;
import org.skhu.skhu9roomthonlogin.member.api.dto.request.MemberLoginReqDto;
import org.skhu.skhu9roomthonlogin.member.api.dto.request.MemberSaveReqDto;
import org.skhu.skhu9roomthonlogin.member.api.dto.response.MemberLoginResDto;
import org.skhu.skhu9roomthonlogin.member.domain.Member;
import org.skhu.skhu9roomthonlogin.member.domain.Role;
import org.skhu.skhu9roomthonlogin.member.domain.repository.MemberRepository;
import org.skhu.skhu9roomthonlogin.member.exception.InvalidMemberException;
import org.skhu.skhu9roomthonlogin.member.exception.NotFoundMemberException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, TokenProvider tokenProvider) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    // 회원가입
    @Transactional
    public void join(MemberSaveReqDto memberSaveReqDto) {
        if (memberRepository.existsByEmail(memberSaveReqDto.email())) {
            throw new InvalidMemberException("이미 존재하는 이메일입니다."); }
        Member member = Member.builder()
                .email(memberSaveReqDto.email())
                .pwd(passwordEncoder.encode(memberSaveReqDto.pwd()))
                .nickname(memberSaveReqDto.nickname())
                .role(Role.ROLE_USER)
                .build();

        memberRepository.save(member);
    }
    // 로그인 (성공 -> 토큰 발급)
    public MemberLoginResDto login(MemberLoginReqDto memberLoginReqDto) {
        Member member = memberRepository.findByEmail(memberLoginReqDto.email()).orElseThrow(NotFoundMemberException::new);
        String token = tokenProvider.generateToken(member.getEmail());

        if (!passwordEncoder.matches(memberLoginReqDto.pwd(), member.getPwd()))
        {
            throw new InvalidMemberException("비밀번호가 일치하지 않습니다.");
        }

        return MemberLoginResDto.of(member, token);
    }
}