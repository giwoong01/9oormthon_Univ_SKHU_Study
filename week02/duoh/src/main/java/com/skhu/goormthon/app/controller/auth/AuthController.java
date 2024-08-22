package com.skhu.goormthon.app.controller.auth;

import com.skhu.goormthon.app.dto.auth.reqDto.*;
import com.skhu.goormthon.app.dto.auth.resDto.AuthResDto;
import com.skhu.goormthon.app.service.auth.*;
import com.skhu.goormthon.app.service.auth.oauth.GithubOAuthService;
import com.skhu.goormthon.app.service.auth.oauth.NaverOAuthService;
import com.skhu.goormthon.global.template.ApiResTemplate;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final EmailService emailService;
    private final SignUpService signUpService;
    private final LoginService loginService;
    private final TokenRenewService tokenRenewService;
    private final NaverOAuthService naverOAuthService;
    private final GithubOAuthService githubOAuthService;

    @PostMapping("/email-send")
    public ResponseEntity<ApiResTemplate<Void>> sendCode(@Valid @RequestBody EmailSendReqDto reqDto) {
        ApiResTemplate<Void> data = emailService.sendVerificationCode(reqDto);
        return ResponseEntity.status(data.getStatus()).body(data);
    }

    @PostMapping("/email-verify")
    public ResponseEntity<ApiResTemplate<Void>> verifyCode(@Valid @RequestBody EmailVerifyReqDto reqDto) {
        ApiResTemplate<Void> data = emailService.verifyCode(reqDto);
        return ResponseEntity.status(data.getStatus()).body(data);
    }

    @PostMapping("/signUp")
    public ResponseEntity<ApiResTemplate<AuthResDto>> signUp(@Valid @RequestBody SignUpReqDto reqDto) {
        ApiResTemplate<AuthResDto> data = signUpService.signUp(reqDto);
        return ResponseEntity.status(data.getStatus()).body(data);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResTemplate<AuthResDto>> login(@Valid @RequestBody LoginReqDto reqDto) {
        ApiResTemplate<AuthResDto> data = loginService.login(reqDto);
        return ResponseEntity.status(data.getStatus()).body(data);
    }

    @PostMapping("/renew")
    public ResponseEntity<ApiResTemplate<AuthResDto>> renewAccessToken(@Valid @RequestBody RefreshTokenReqDto reqDto) {
        ApiResTemplate<AuthResDto> data = tokenRenewService.renewAccessToken(reqDto);
        return ResponseEntity.status(data.getStatus()).body(data);
    }

    @GetMapping("/callback/naver")
    public ResponseEntity<ApiResTemplate<AuthResDto>> naverCallback(@RequestParam String code, @RequestParam String state) {
        ApiResTemplate<AuthResDto> data = naverOAuthService.signUpOrLogin(naverOAuthService.getAccessToken(code, state).getData());
        return ResponseEntity.status(data.getStatus()).body(data);
    }

    @GetMapping("/callback/github")
    public ResponseEntity<ApiResTemplate<AuthResDto>> githubCallback(@RequestParam String code) {
        ApiResTemplate<AuthResDto> data = githubOAuthService.signUpOrLogin(githubOAuthService.getAccessToken(code).getData());
        return ResponseEntity.status(data.getStatus()).body(data);
    }
}