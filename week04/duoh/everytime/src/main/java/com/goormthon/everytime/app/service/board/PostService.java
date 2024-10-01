package com.goormthon.everytime.app.service.board;

import com.goormthon.everytime.app.domain.board.Board;
import com.goormthon.everytime.app.domain.board.BoardName;
import com.goormthon.everytime.app.domain.board.post.Post;
import com.goormthon.everytime.app.domain.board.post.PostImage;
import com.goormthon.everytime.app.domain.Image;
import com.goormthon.everytime.app.domain.user.User;
import com.goormthon.everytime.app.dto.board.reqDto.PostReqDto;
import com.goormthon.everytime.app.dto.board.resDto.CommentResDto;
import com.goormthon.everytime.app.dto.board.resDto.PostDetailResDto;
import com.goormthon.everytime.app.repository.*;
import com.goormthon.everytime.global.exception.CustomException;
import com.goormthon.everytime.global.exception.code.ErrorCode;
import com.goormthon.everytime.global.exception.code.SuccessCode;
import com.goormthon.everytime.global.service.S3Service;
import com.goormthon.everytime.global.template.ApiResTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private static final Set<String> IMAGE_EXTENSIONS = Set.of(".jpg", ".jpeg", ".png");

    private final BoardRepository boardRepository;
    private final PostRepository postRepository;
    private final ImageRepository imageRepository;
    private final PostImageRepository postImageRepository;
    private final UserRepository userRepository;
    private final S3Service s3Service;
    private final CommentRepository commentRepository;

    @Transactional
    public ApiResTemplate<Void> uploadPost(
            PostReqDto reqDto, Principal principal, int boardId) {

        Long userId = Long.parseLong(principal.getName());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND, ErrorCode.USER_NOT_FOUND.getMessage()));

        BoardName boardName = BoardName.fromId(boardId);
        Board board = boardRepository.findByBoardNameAndUniversity(boardName, user.getUniversity())
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND, ErrorCode.BOARD_NOT_FOUND.getMessage()));

        Post post = postRepository.save(reqDto.toEntity(board, user));

        if (reqDto.files() != null && !reqDto.files().isEmpty()) {
            saveImages(reqDto.files(), post);
        }

        return ApiResTemplate.success(SuccessCode.POST_UPLOAD_SUCCESS, null);
    }

    private void saveImages(List<MultipartFile> files, Post post) {
        for (MultipartFile file : files) {
            if (!isValidImageExtension(file)) {
                throw new CustomException(ErrorCode.INVALID_FILE_TYPE, ErrorCode.INVALID_FILE_TYPE.getMessage());
            }

            String imageUrl;
            try {
                imageUrl = s3Service.upload(file, "post-images");
            } catch (IOException e) {
                throw new CustomException(ErrorCode.FILE_UPLOAD_ERROR, ErrorCode.FILE_UPLOAD_ERROR.getMessage());
            }

            Image image = imageRepository.save(Image.builder()
                    .imageUrl(imageUrl)
                    .build());

            postImageRepository.save(PostImage.builder()
                    .post(post)
                    .image(image)
                    .build());
        }
    }

    private boolean isValidImageExtension(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        return IMAGE_EXTENSIONS.stream().anyMatch(fileName::endsWith);
    }

    public ApiResTemplate<PostDetailResDto> getPost(int boardId, Long postId, Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND, ErrorCode.USER_NOT_FOUND.getMessage()));

        BoardName boardName = BoardName.fromId(boardId);
        Board board = boardRepository.findByBoardNameAndUniversity(boardName, user.getUniversity())
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND, ErrorCode.BOARD_NOT_FOUND.getMessage()));

        Post post = postRepository.findByPostIdAndBoard(postId, board)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));

        int commentsCount = commentRepository.countByPost(post);

        List<CommentResDto> comments = commentRepository.findAllByPost(post).stream()
                .map(CommentResDto::from)
                .toList();

        PostDetailResDto resDto = PostDetailResDto.of(post, commentsCount, comments);

        return ApiResTemplate.success(SuccessCode.GET_POST_SUCCESS, resDto);
    }
}
