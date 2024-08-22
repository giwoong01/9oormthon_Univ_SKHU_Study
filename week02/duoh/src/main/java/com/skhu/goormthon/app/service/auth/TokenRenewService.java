package com.skhu.goormthon.app.service.auth;

import com.skhu.goormthon.app.domain.member.Member;
import com.skhu.goormthon.app.dto.auth.reqDto.RefreshTokenReqDto;
import com.skhu.goormthon.app.dto.auth.resDto.AuthResDto;
import com.skhu.goormthon.app.repository.MemberRepository;
import com.skhu.goormthon.global.exception.CustomException;
import com.skhu.goormthon.global.exception.code.ErrorCode;
import com.skhu.goormthon.global.exception.code.SuccessCode;
import com.skhu.goormthon.global.jwt.TokenProvider;
import com.skhu.goormthon.global.template.ApiResTemplate;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenRenewService {

    private static final String REFRESH_TOKEN_PREFIX = "refreshToken:";

    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final RedisTemplate<String, String> redisTemplate;

    @Transactional
    public ApiResTemplate<AuthResDto> renewAccessToken(RefreshTokenReqDto reqDto) {
        String key = REFRESH_TOKEN_PREFIX + reqDto.refreshToken();
        String memberIdStr = redisTemplate.opsForValue().get(key);

        if (memberIdStr == null) {
            throw new CustomException(ErrorCode.INVALID_TOKEN_EXCEPTION, ErrorCode.INVALID_TOKEN_EXCEPTION.getMessage());
        }

        if (!tokenProvider.validateToken(reqDto.refreshToken())) {
            throw new CustomException(ErrorCode.INVALID_TOKEN_EXCEPTION, ErrorCode.INVALID_TOKEN_EXCEPTION.getMessage());
        }

        Long memberId = Long.parseLong(memberIdStr);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MEMBER_EXCEPTION,
                        ErrorCode.NOT_FOUND_MEMBER_EXCEPTION.getMessage()));

        String renewAccessToken = tokenProvider.createAccessToken(member);

        return ApiResTemplate.success(SuccessCode.RENEW_TOKEN_SUCCESS, AuthResDto.of(renewAccessToken, reqDto.refreshToken()));
    }

    public void saveRefreshToken(String refreshToken, Long memberId) {
        String key = REFRESH_TOKEN_PREFIX + refreshToken;
        redisTemplate.opsForValue().set(key, memberId.toString());
    }
}
