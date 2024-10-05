package org.goorm.everytime.board.service;

import lombok.RequiredArgsConstructor;
import org.goorm.everytime.board.api.dto.posts.PostUploadDto;
import org.goorm.everytime.board.domain.Boards;
import org.goorm.everytime.board.domain.Post;
import org.goorm.everytime.board.domain.repository.BoardsRespository;
import org.goorm.everytime.board.domain.repository.PostRepository;
import org.goorm.everytime.member.domain.Member;
import org.goorm.everytime.member.domain.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class PostUploader {

    private final BoardsRespository boardsRespository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final ImageService imageService;

    public void uploadPost(PostUploadDto postUploadDto, Principal principal) {
        validateImageFiles(postUploadDto.files());

        Boards board = boardsRespository.findById(postUploadDto.boardId())
                .orElseThrow(() -> new IllegalArgumentException("게시판이 존재하지 않습니다."));
        Member member = memberRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

        Post post = getPost(postUploadDto, board, member);

        saveImagesFromDto(postUploadDto, post);
        postRepository.save(post);
    }

    private static Post getPost(PostUploadDto postUploadDto, Boards board, Member member) {
        return Post.builder()
                .title(postUploadDto.title())
                .content(postUploadDto.content())
                .anonym(postUploadDto.anonym())
                .board(board)
                .member(member)
                .build();
    }

    private void saveImagesFromDto(PostUploadDto postUploadDto, Post post) {
        for (MultipartFile file : postUploadDto.files()) {
            imageService.uploadFile(file, post);
        }
    }

    private void validateImageFiles(MultipartFile[] files) {
        for (MultipartFile file : files) {
            if (!isImageFile(file)) {
                throw new IllegalArgumentException("이미지 파일만 업로드할 수 있습니다.");
            }
        }
    }

    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && (contentType.equals("image/jpeg") || contentType.equals("image/png") || contentType.equals("image/gif"));
    }
}
