package org.goorm.everytime.auth.service;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.goorm.everytime.auth.api.dto.AuthReqDto;
import org.goorm.everytime.auth.jwt.JwtBlackList;
import org.goorm.everytime.auth.jwt.TokenProvider;
import org.goorm.everytime.auth.api.dto.TokenDto;
import org.goorm.everytime.global.common.exception.model.RefreshTokenInvalidException;
import org.goorm.everytime.member.domain.Member;
import org.goorm.everytime.member.domain.repository.MemberRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final JwtBlackList jwtBlackList;

    @Transactional
    public TokenDto login(AuthReqDto authReqDto) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = authReqDto.toAuthentication();
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            return tokenProvider.generateTokenDto(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("유효하지 않은 아이디와 비밀번호입니다.", e);
        } catch (AuthenticationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("로그인 중 알 수 없는 에러가 발생했습니다.", e);
        }
    }

    @Transactional
    public TokenDto refresh(HttpServletRequest request) throws RefreshTokenInvalidException {
        String refreshToken = request.getHeader("Authorization").substring(7);
        if (!tokenProvider.validateToken(refreshToken)) {
            throw new RuntimeException("Token이 유효하지 않습니다.");
        }
        String userId = tokenProvider.getUserIdFromRefreshToken(refreshToken);
        Member member = memberRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));

        if(jwtBlackList.isRefreshTokenBlacklisted(refreshToken)){
            throw new RefreshTokenInvalidException("로그아웃 처리되었습니다.");
        }

        return tokenProvider.regenerateToken(member);
    }
}
