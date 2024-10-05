package com.example.everytime.member.api;

import com.example.everytime.auth.dto.TokenDto;
import com.example.everytime.global.template.RspTemplate;
import com.example.everytime.member.api.dto.reqeust.MemberJoinReqDto;
import com.example.everytime.member.api.dto.reqeust.MemberLoginReqDto;
import com.example.everytime.member.api.dto.response.MemberInfoResDto;
import com.example.everytime.member.api.dto.response.MemberLoginResDto;
import com.example.everytime.member.application.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "회원가입", description = "회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공 !"),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @PostMapping("/join")
    public RspTemplate<String> join(@RequestBody @Valid MemberJoinReqDto memberJoinReqDto) {
        memberService.join(memberJoinReqDto);
        return new RspTemplate<>(HttpStatus.CREATED, "회원가입");
    }

    @Operation(summary = "로그인", description = "로그인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공 !"),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @GetMapping("/login")
    public RspTemplate<TokenDto> login(@RequestBody @Valid MemberLoginReqDto memberLoginReqDto) {
        MemberLoginResDto memberLoginResDto = memberService.login(memberLoginReqDto);
        TokenDto tokens = new TokenDto(memberLoginResDto.accessToken(), memberLoginResDto.refreshToken());
        return new RspTemplate<>(HttpStatus.OK, "로그인", tokens);
    }

    @Operation(summary = "프로필", description = "회원 프로필 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 프로필 조회 성공 !"),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @GetMapping("/profile")
    public MemberInfoResDto getMemberInfo(Principal principal) {
        return memberService.getMemberInfo(principal);
    }
}
