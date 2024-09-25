package com.example.everytime.member.api;

import com.example.everytime.global.template.RspTemplate;
import com.example.everytime.member.api.dto.reqeust.MemberJoinReqDto;
import com.example.everytime.member.api.dto.reqeust.MemberLoginReqDto;
import com.example.everytime.member.api.dto.response.MemberInfoResDto;
import com.example.everytime.member.api.dto.response.MemberLoginResDto;
import com.example.everytime.member.application.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/join")
    public RspTemplate<String> join(@RequestBody @Valid MemberJoinReqDto memberJoinReqDto) {
        memberService.join(memberJoinReqDto);
        return new RspTemplate<>(HttpStatus.CREATED, "회원가입");
    }

    @GetMapping("/login")
    public RspTemplate<Map<String, String>> login(@RequestBody @Valid MemberLoginReqDto memberLoginReqDto) {
        MemberLoginResDto memberLoginResDto = memberService.login(memberLoginReqDto);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", memberLoginResDto.accessToken());
        tokens.put("refreshToken", memberLoginResDto.refreshToken());

        return new RspTemplate<>(HttpStatus.OK, "로그인", tokens);
    }

    @GetMapping("/profile")
    public MemberInfoResDto getMemberInfo(Principal principal) {
        return memberService.getMemberInfo(principal);
    }
}
