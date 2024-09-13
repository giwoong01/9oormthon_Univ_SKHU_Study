package org.skhu.skhu9roomthonlogin.post.application;

import org.skhu.skhu9roomthonlogin.member.domain.Member;
import org.skhu.skhu9roomthonlogin.member.domain.repository.MemberRepository;
import org.skhu.skhu9roomthonlogin.member.exception.NotFoundMemberException;
import org.skhu.skhu9roomthonlogin.post.api.dto.request.PostSaveReqDto;
import org.skhu.skhu9roomthonlogin.post.api.dto.response.PostInfoResDto;
import org.skhu.skhu9roomthonlogin.post.api.dto.response.PostListResDto;
import org.skhu.skhu9roomthonlogin.post.domain.Post;
import org.skhu.skhu9roomthonlogin.post.domain.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class PostService {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    public PostService(MemberRepository memberRepository, PostRepository postRepository) {
        this.memberRepository = memberRepository;
        this.postRepository = postRepository;
    }

    @Transactional
    public void postSave(String email, PostSaveReqDto postSaveReqDto) {
        Member member = memberRepository.findByEmail(email).orElseThrow(NotFoundMemberException::new);

        postRepository.save(Post.builder()
                .title(postSaveReqDto.title())
                .content(postSaveReqDto.content())
                .member(member)
                .build());
    }

    // Read (작성자에 따른 게시물 리스트)
    public PostListResDto postFindMember(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(IllegalArgumentException::new);

        List<Post> posts = postRepository.findByMember(member);
        List<PostInfoResDto> postInfoResDtoList = posts.stream()
                .map(PostInfoResDto::from)
                .toList();

        return PostListResDto.from(postInfoResDtoList);
    }
}
