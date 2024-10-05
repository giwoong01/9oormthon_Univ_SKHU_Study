package org.goorm.everytime.member.service;

import lombok.RequiredArgsConstructor;
import org.goorm.everytime.auth.domain.SocialType;
import org.goorm.everytime.member.api.dto.JoinDto;
import org.goorm.everytime.member.domain.Authority;
import org.goorm.everytime.member.domain.Member;
import org.goorm.everytime.member.domain.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private static final String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$";

    //회원정보 저장
    @Transactional
    public void join(JoinDto joinDto) {
        isNotDuplicatedUsername(joinDto.username());
        isValidPassword(joinDto.password());
        checkPassword(joinDto.password(), joinDto.checkPassword());
        isValidUniversityName(joinDto.universityName());
        Member member = Member.builder()
                .username(joinDto.username())
                .password(passwordEncoder.encode(joinDto.password()))
                .nickname(joinDto.nickname())
                .authority(Authority.ROLE_USER)
                .socialType(SocialType.SELF)
                .email(joinDto.email())
                .year(joinDto.year())
                .universityName(joinDto.universityName())
                .name(joinDto.name())
                .build();
        memberRepository.save(member);
    }
    
    private void isNotDuplicatedUsername(String username) {
        if (memberRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
    }

    private void isValidPassword(String password) {
        if (!password.matches(passwordPattern)) {
            throw new IllegalArgumentException("비밀번호는 대소문자, 특수문자, 숫자가 포함되어야 합니다. ");
        }
    }
    
    private void isValidUniversityName(String universityName) {
        if (!universityName.endsWith("대")) {
            throw new IllegalArgumentException("대학교 이름은 ~대로 끝나야 합니다.");
        }
    }

    private void checkPassword(String password, String checkPassword){
        if (!password.equals(checkPassword)) {
            throw new IllegalArgumentException("비밀번호와 비밀번호 확인의 값이 동일한지 비밀번호를 다시 확인해주세요.");
        }
    }

    @Transactional
    public void deleteMember(String username) {
        memberRepository.deleteMemberByUsername(username);
    }
}
