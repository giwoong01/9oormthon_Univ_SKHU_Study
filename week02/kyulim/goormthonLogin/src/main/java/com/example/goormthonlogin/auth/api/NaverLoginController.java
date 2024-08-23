package com.example.goormthonlogin.auth.api;

import com.example.goormthonlogin.auth.application.NaverLoginService;
import com.example.goormthonlogin.auth.dto.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class NaverLoginController {

    private final NaverLoginService naverLoginService;

    @GetMapping("/naver") // 네이버 인가 코드 받음
    public Token naverCallback(@RequestParam(name = "code") String code,
                               @RequestParam(name = "state") String state) {
        String naverAccessToken = naverLoginService.getNaverAccessToken(code, state);
        return naverLoginService.loginOrSignUp(naverAccessToken);
    }
}
