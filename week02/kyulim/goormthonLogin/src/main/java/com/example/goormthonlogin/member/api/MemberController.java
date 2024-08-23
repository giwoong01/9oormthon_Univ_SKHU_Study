package com.example.goormthonlogin.member.api;

import com.example.goormthonlogin.member.api.dto.MemberLoginReqDto;
import com.example.goormthonlogin.member.api.dto.MemberLoginResDto;
import com.example.goormthonlogin.member.api.dto.MemberSaveReqDto;
import com.example.goormthonlogin.member.application.MemberService;
import com.example.goormthonlogin.auth.global.templete.RspTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping()
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/signup")
    public RspTemplate<String> join(@RequestBody @Valid MemberSaveReqDto memberSaveReqDto) {
        memberService.join(memberSaveReqDto);
        return new RspTemplate<>(HttpStatus.CREATED, "회원가입");
    }

    @GetMapping()
    public RspTemplate<MemberLoginResDto> login(@RequestBody @Valid MemberLoginReqDto memberLoginReqDto) {
        MemberLoginResDto memberLoginResDto = memberService.login(memberLoginReqDto);
        return new RspTemplate<>(HttpStatus.OK, "로그인", memberLoginResDto);
    }
}
