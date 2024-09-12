package com.skhu.goormthon.app.service.member;

import com.skhu.goormthon.app.domain.member.Member;
import com.skhu.goormthon.app.dto.member.MemberInfoDto;
import com.skhu.goormthon.app.repository.MemberRepository;
import com.skhu.goormthon.global.exception.CustomException;
import com.skhu.goormthon.global.exception.code.ErrorCode;
import com.skhu.goormthon.global.exception.code.SuccessCode;
import com.skhu.goormthon.global.template.ApiResTemplate;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberInfoService {

    private final MemberRepository memberRepository;

    public ApiResTemplate<MemberInfoDto> getMemberInfo(Principal principal) {

        Long memberId = Long.parseLong(principal.getName());
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MEMBER_EXCEPTION, ErrorCode.NOT_FOUND_MEMBER_EXCEPTION.getMessage()));

        return ApiResTemplate.success(SuccessCode.GET_MEMBER_INFO_SUCCESS, MemberInfoDto.from(member));
    }
}
