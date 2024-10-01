package com.goormthon.everytime.app.controller.board;

import com.goormthon.everytime.app.dto.board.resDto.MyPostResDto;
import com.goormthon.everytime.app.service.board.MyActivityService;
import com.goormthon.everytime.global.template.ApiResTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "사용자 활동", description = "사용자 활동(게시글, 댓글, 스크랩)을 조회하는 api 그룹")
public class MyActivityController {

    private final MyActivityService myActivityService;

    @GetMapping("/myPost")
    @Operation(
            summary = "사용자가 작성한 게시글 조회",
            description = "사용자가 작성한 게시글들을 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "내가 쓴 글 조회 성공"),
                    @ApiResponse(responseCode = "403", description = "권한 문제"),
                    @ApiResponse(responseCode = "404", description = "사용자의 게시글을 찾을 수 없음"),
                    @ApiResponse(responseCode = "500", description = "서버 문제 or 관리자 문의")
            }
    )
    public ResponseEntity<ApiResTemplate<List<MyPostResDto>>> getMyPosts(Principal principal) {
        ApiResTemplate<List<MyPostResDto>> data = myActivityService.getMyPosts(principal);
        return ResponseEntity.status(data.getStatusCode()).body(data);
    }
}
