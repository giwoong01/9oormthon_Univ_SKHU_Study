package org.skhu.skhu9roomthonlogin.post.api.dto;

import jakarta.validation.Valid;
import org.skhu.skhu9roomthonlogin.global.template.RspTemplate;
import org.skhu.skhu9roomthonlogin.post.api.dto.request.PostSaveReqDto;
import org.skhu.skhu9roomthonlogin.post.api.dto.response.PostListResDto;
import org.skhu.skhu9roomthonlogin.post.application.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping()
    public RspTemplate<String> postSave(@AuthenticationPrincipal String email,
                                        @RequestBody @Valid PostSaveReqDto postSaveReqDto) {
        postService.postSave(email, postSaveReqDto);
        return new RspTemplate<>(HttpStatus.CREATED, "게시물 생성");
    }

    @GetMapping()
    public ResponseEntity<PostListResDto> myPostFindAll(@AuthenticationPrincipal String email) {
        PostListResDto postListResDto = postService.postFindMember(email);
        return new ResponseEntity<>(postListResDto, HttpStatus.OK);
    }

}
