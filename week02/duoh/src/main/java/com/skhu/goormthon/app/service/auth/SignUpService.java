package com.skhu.goormthon.app.service.auth;

import com.skhu.goormthon.app.domain.member.Member;
import com.skhu.goormthon.app.dto.auth.reqDto.SignUpReqDto;
import com.skhu.goormthon.app.dto.auth.resDto.AuthResDto;
import com.skhu.goormthon.app.repository.MemberRepository;
import com.skhu.goormthon.global.exception.CustomException;
import com.skhu.goormthon.global.exception.code.ErrorCode;
import com.skhu.goormthon.global.exception.code.SuccessCode;
import com.skhu.goormthon.global.jwt.TokenProvider;
import com.skhu.goormthon.global.template.ApiResTemplate;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final TokenRenewService tokenRenewService;
    private final EmailService emailService;

    @Transactional
    public ApiResTemplate<AuthResDto> signUp(SignUpReqDto signUpReqDto) {
        if (memberRepository.existsByEmail(signUpReqDto.email())) {
            throw new CustomException(ErrorCode.ALREADY_EXIST_MEMBER_EXCEPTION, ErrorCode.ALREADY_EXIST_MEMBER_EXCEPTION.getMessage());
        }

        if (!emailService.isEmailVerified(signUpReqDto.email())) {
            throw new CustomException(ErrorCode.EMAIL_NOT_VERIFIED_EXCEPTION, ErrorCode.EMAIL_NOT_VERIFIED_EXCEPTION.getMessage());
        }

        if (!signUpReqDto.password().equals(signUpReqDto.confirmPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_MISMATCH_EXCEPTION,
                    ErrorCode.PASSWORD_MISMATCH_EXCEPTION.getMessage());
        }

        String encodedPassword = passwordEncoder.encode(signUpReqDto.password());
        Member member = signUpReqDto.toEntity(encodedPassword);
        memberRepository.save(member);

        emailService.removeVerifiedEmail(signUpReqDto.email());

        String accessToken = tokenProvider.createAccessToken(member);
        String refreshToken = tokenProvider.createRefreshToken(member);
        tokenRenewService.saveRefreshToken(refreshToken, member.getMemberId());

        return ApiResTemplate.success(SuccessCode.SIGNUP_MEMBER_SUCCESS, AuthResDto.of(accessToken, refreshToken));
    }
}