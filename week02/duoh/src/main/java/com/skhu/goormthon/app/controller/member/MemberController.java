package com.skhu.goormthon.app.controller.member;

import com.skhu.goormthon.app.dto.member.MemberInfoDto;
import com.skhu.goormthon.app.service.member.MemberInfoService;
import com.skhu.goormthon.global.template.ApiResTemplate;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberInfoService memberInfoService;

    @GetMapping("/info")
    public ResponseEntity<ApiResTemplate<MemberInfoDto>> getMemberInfo(Principal principal) {
        ApiResTemplate<MemberInfoDto> data = memberInfoService.getMemberInfo(principal);
        return ResponseEntity.status(data.getStatus()).body(data);
    }
}
