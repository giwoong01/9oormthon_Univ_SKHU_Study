package com.goormthon.everytime.app.service.board;

import com.goormthon.everytime.app.domain.board.post.Post;
import com.goormthon.everytime.app.domain.user.User;
import com.goormthon.everytime.app.dto.board.resDto.MyPostResDto;
import com.goormthon.everytime.app.repository.CommentRepository;
import com.goormthon.everytime.app.repository.PostRepository;
import com.goormthon.everytime.app.repository.UserRepository;
import com.goormthon.everytime.global.exception.CustomException;
import com.goormthon.everytime.global.exception.code.ErrorCode;
import com.goormthon.everytime.global.exception.code.SuccessCode;
import com.goormthon.everytime.global.template.ApiResTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyActivityService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public ApiResTemplate<List<MyPostResDto>> getMyPosts(Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND, ErrorCode.USER_NOT_FOUND.getMessage()));

        List<Post> posts = postRepository.findAllByUser(user);

        List<MyPostResDto> resDtos = posts.stream()
                .map(post -> {
                    int commentsCount = commentRepository.countByPost(post);
                    return MyPostResDto.of(post, commentsCount);
                })
                .collect(Collectors.toList());

        return ApiResTemplate.success(SuccessCode.GET_MY_POSTS_SUCCESS, resDtos);

    }
}
