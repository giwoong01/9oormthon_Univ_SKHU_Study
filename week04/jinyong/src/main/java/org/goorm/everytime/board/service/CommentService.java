package org.goorm.everytime.board.service;

import lombok.RequiredArgsConstructor;
import org.goorm.everytime.board.api.dto.comments.CommentAddDto;
import org.goorm.everytime.board.domain.Comment;
import org.goorm.everytime.board.domain.Post;
import org.goorm.everytime.board.domain.repository.CommentRepository;
import org.goorm.everytime.board.domain.repository.PostRepository;
import org.goorm.everytime.member.domain.Member;
import org.goorm.everytime.member.domain.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void addComment(CommentAddDto commentAddDto, Long postId,Principal principal) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        Member member = memberRepository.findByUsername(principal.getName()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        commentRepository.save(new Comment(commentAddDto.comment(), commentAddDto.timeStamp(), post, member));
    }
}
