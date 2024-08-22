package com.example.loginstudy.auth.api;

import com.example.loginstudy.auth.api.dto.request.JoinReqDto;
import com.example.loginstudy.auth.api.dto.request.LoginReqDto;
import com.example.loginstudy.auth.api.dto.request.RefreshTokenReqDto;
import com.example.loginstudy.auth.api.dto.response.MemberLoginResDto;
import com.example.loginstudy.auth.api.dto.response.UserInfo;
import com.example.loginstudy.auth.application.AuthMemberService;
import com.example.loginstudy.auth.application.AuthService;
import com.example.loginstudy.auth.application.AuthServiceFactory;
import com.example.loginstudy.auth.application.TokenService;
import com.example.loginstudy.global.jwt.api.dto.TokenDto;
import com.example.loginstudy.global.template.RspTemplate;
import com.example.loginstudy.member.domain.SocialType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    private final AuthServiceFactory authServiceFactory;
    private final AuthMemberService memberService;
    private final TokenService tokenService;

    @PostMapping("/{provider}/token")
    public RspTemplate<TokenDto> getToken(
            @PathVariable(name = "provider") String provider,
            @RequestParam(name = "code") String code) {
        AuthService authService = authServiceFactory.getAuthService(provider);
        UserInfo userInfo = authService.getUserInfo(code);

        MemberLoginResDto getMemberDto = memberService.saveUserInfo(userInfo,
                SocialType.valueOf(provider.toUpperCase()));
        TokenDto getToken = tokenService.getToken(getMemberDto);

        return new RspTemplate<>(HttpStatus.OK, "소셜 로그인 토큰 발급", getToken);
    }

    @PostMapping("/{provider}/join")
    public RspTemplate<Void> join(@PathVariable(name = "provider") String provider,
                                  @RequestBody JoinReqDto joinReqDto) {
        memberService.join(SocialType.valueOf(provider.toUpperCase()), joinReqDto);

        return new RspTemplate<>(HttpStatus.OK, "회원 가입");
    }

    @PostMapping("/{provider}/login")
    public RspTemplate<TokenDto> login(@PathVariable(name = "provider") String provider,
                                       @RequestBody LoginReqDto loginReqDto) {
        MemberLoginResDto getMemberDto = memberService.login(SocialType.valueOf(provider.toUpperCase()), loginReqDto);
        TokenDto getToken = tokenService.getToken(getMemberDto);

        return new RspTemplate<>(HttpStatus.OK, "자체 로그인 토큰 발급", getToken);
    }

    @PostMapping("/token/access")
    public RspTemplate<TokenDto> generateAccessToken(@RequestBody RefreshTokenReqDto refreshTokenReqDto) {
        TokenDto getToken = tokenService.generateAccessToken(refreshTokenReqDto);

        return new RspTemplate<>(HttpStatus.OK, "액세스 토큰 발급", getToken);
    }

}
