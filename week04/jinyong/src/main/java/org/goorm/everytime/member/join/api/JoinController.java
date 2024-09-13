package org.goorm.everytime.member.join.api;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.goorm.everytime.global.common.dto.BaseResponse;
import org.goorm.everytime.global.common.exception.SuccessCode;
import org.goorm.everytime.member.join.api.dto.JoinDto;
import org.goorm.everytime.member.join.service.JoinService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JoinController {
    private final JoinService joinService;

    //회원가입
    @Operation(summary = "회원가입")
    @Parameters({
            @Parameter(name = "username", description = "아이디", required = true),
            @Parameter(name = "password", description = "비밀번호", required = true),
            @Parameter(name = "checkPassword", description = "비밀번호 확인", required = true),
            @Parameter(name = "nickname", description = "닉네임", required = true),
            @Parameter(name = "email", description = "이메일", required = true),
            @Parameter(name = "year", description = "학년", required = true),
            @Parameter(name = "universityName", description = "대학교 이름", required = true),
            @Parameter(name = "name", description = "이름", required = true)
    })
    @PostMapping("/join")
    public BaseResponse join(@Valid @RequestBody JoinDto joinDto) {
        joinService.join(joinDto);
        return BaseResponse.success(SuccessCode.USER_JOIN_SUCCESS);
    }
}
