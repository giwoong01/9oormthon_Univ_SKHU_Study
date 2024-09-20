package com.goormthon.everytime.app.controller.user;

import com.goormthon.everytime.app.dto.user.resDto.UserInfoResDto;
import com.goormthon.everytime.app.service.user.UserInfoService;
import com.goormthon.everytime.global.template.ApiResTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@Tag(name = "사용자 프로필", description = "사용자 프로필을 담당하는 api 그룹")
public class UserController {

    private final UserInfoService userInfoService;

    @GetMapping("/user/profile")
    @Operation(
            summary = "사용자 프로필 조회",
            description = "현재 로그인된 사용자의 프로필을 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "사용자 프로필 조회 성공"),
                    @ApiResponse(responseCode = "403", description = "권한 문제"),
                    @ApiResponse(responseCode = "404", description = "사용자 정보를 찾을 수 없음"),
                    @ApiResponse(responseCode = "500", description = "서버 문제 or 관리자 문의")
            })
    public ResponseEntity<ApiResTemplate<UserInfoResDto>> getUserInfo(Principal principal) {
        ApiResTemplate<UserInfoResDto> data = userInfoService.getUserInfo(principal);
        return ResponseEntity.status(data.getStatus()).body(data);
    }
}
