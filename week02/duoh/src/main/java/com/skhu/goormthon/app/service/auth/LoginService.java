package com.skhu.goormthon.app.service.auth;

import com.skhu.goormthon.app.domain.member.Member;
import com.skhu.goormthon.app.dto.auth.reqDto.LoginReqDto;
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
public class LoginService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final TokenRenewService tokenRenewService;

    @Transactional
    public ApiResTemplate<AuthResDto> login(LoginReqDto loginReqDto) {
        Member member = memberRepository.findByEmail(loginReqDto.email())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_EMAIL_EXCEPTION,
                        ErrorCode.NOT_FOUND_EMAIL_EXCEPTION.getMessage()));

        if (!passwordEncoder.matches(loginReqDto.password(), member.getPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_MISMATCH_EXCEPTION,
                    ErrorCode.PASSWORD_MISMATCH_EXCEPTION.getMessage());
        }

        String accessToken = tokenProvider.createAccessToken(member);
        String refreshToken = tokenProvider.createRefreshToken(member);
        tokenRenewService.saveRefreshToken(refreshToken, member.getMemberId());

        return ApiResTemplate.success(SuccessCode.LOGIN_MEMBER_SUCCESS, AuthResDto.of(accessToken, refreshToken));
    }
}
