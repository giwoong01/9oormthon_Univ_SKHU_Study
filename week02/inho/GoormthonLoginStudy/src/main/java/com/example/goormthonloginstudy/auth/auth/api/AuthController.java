package com.example.goormthonloginstudy.auth.auth.api;

import com.example.goormthonloginstudy.auth.auth.api.dto.request.JoinReqDto;
import com.example.goormthonloginstudy.auth.auth.api.dto.request.LoginReqDto;
import com.example.goormthonloginstudy.auth.auth.api.dto.request.TokenReqDto;
import com.example.goormthonloginstudy.auth.auth.api.dto.response.MemberLoginResDto;
import com.example.goormthonloginstudy.auth.auth.api.dto.response.UserInfo;
import com.example.goormthonloginstudy.auth.auth.application.AuthMemberService;
import com.example.goormthonloginstudy.auth.auth.application.AuthService;
import com.example.goormthonloginstudy.auth.auth.application.AuthServiceFactory;
import com.example.goormthonloginstudy.global.template.RspTemplate;
import com.example.goormthonloginstudy.member.domain.SocialType;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceFactory authServiceFactory;
    private final AuthMemberService memberService;

    @GetMapping("oauth2/callback/google")
    public JsonNode googleCallback(@RequestParam(name = "code") String code) {
        AuthService googleAuthService = authServiceFactory.getAuthService("google");
        return googleAuthService.getIdToken(code);
    }

    @GetMapping("oauth2/callback/kakao")
    public JsonNode kakaoCallback(@RequestParam(name = "code") String code) {
        AuthService kakaoAuthService = authServiceFactory.getAuthService("kakao");
        return kakaoAuthService.getIdToken(code);
    }

    @PostMapping("/{provider}/token")
    public RspTemplate<MemberLoginResDto> generateMemberAndSesstion(
            @PathVariable(name = "provider") String provider,
            @RequestBody TokenReqDto tokenReqDto,
            HttpServletRequest request) {

        AuthService authService = authServiceFactory.getAuthService(provider);
        UserInfo userInfo = authService.getUserInfo(tokenReqDto.authCode());

        // 회원 정보 저장
        MemberLoginResDto getMemberDto = memberService.saveUserInfo(userInfo,
                SocialType.valueOf(provider.toUpperCase()));

        // SecurityContext에 인증 정보 설정 그 후에 세션에 해당 정보 저장
        setUpSpringSecurityContext(getMemberDto, request);

        return new RspTemplate<>(HttpStatus.OK, "소셜 로그인 완료", getMemberDto);
    }

    @PostMapping("/{provider}/join")
    public RspTemplate<Void> join(@PathVariable(name = "provider") String provider,
                                  @RequestBody JoinReqDto joinReqDto) {
        memberService.join(SocialType.valueOf(provider.toUpperCase()), joinReqDto);

        return new RspTemplate<>(HttpStatus.OK, "회원 가입");
    }

    @PostMapping("/{provider}/login")
    public RspTemplate<MemberLoginResDto> login(@PathVariable(name = "provider") String provider,
                                                @RequestBody LoginReqDto loginReqDto,
                                                HttpServletRequest request) {
        MemberLoginResDto getMemberDto = memberService.login(
                SocialType.valueOf(provider.toUpperCase()),loginReqDto);

        setUpSpringSecurityContext(getMemberDto, request);

        return new RspTemplate<>(HttpStatus.OK, "자체 로그인 완료", getMemberDto);
    }

    @PostMapping("/logout")
    public RspTemplate<String> logout(HttpServletRequest request) {
        // 현재 세션을 가져옴
        HttpSession session = request.getSession(false);
        if (session != null) {
            // 세션 무효화
            session.invalidate();
        }

        SecurityContextHolder.clearContext();

        return new RspTemplate<>(HttpStatus.OK, "로그아웃 완료", "로그아웃 되었습니다.");
    }

    private void setUpSpringSecurityContext(MemberLoginResDto memberLoginResDto, HttpServletRequest request) {
        // 사용자 인증 객체 생성
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(memberLoginResDto.findMember().getEmail(), null, List.of(new SimpleGrantedAuthority("ROLE_USER")));

        // SecurityContext에 인증 객체 설정
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 세션에 SecurityContext 설정
        HttpSession session = request.getSession(true);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
    }


}
