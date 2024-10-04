package org.skhu.everytime.community.board.application;

import lombok.RequiredArgsConstructor;
import org.skhu.everytime.community.board.api.dto.request.PostRequestDto;
import org.skhu.everytime.community.board.api.dto.response.BoardResponseDto;
import org.skhu.everytime.community.board.api.dto.response.PostResponseDto;
import org.skhu.everytime.community.board.domain.Board;
import org.skhu.everytime.community.board.domain.Comment;
import org.skhu.everytime.community.board.domain.Post;
import org.skhu.everytime.community.board.domain.Scrap;
import org.skhu.everytime.community.board.domain.repository.CommentRepository;
import org.skhu.everytime.community.board.domain.repository.PostRepository;
import org.skhu.everytime.community.board.domain.repository.ScrapRepository;
import org.skhu.everytime.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.skhu.everytime.user.User;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ScrapRepository scrapRepository;
    private final UserRepository userRepository;

    public List<PostResponseDto> getMyArticles(User user) {
        return postRepository.findByUser(user).stream()
                .map(this::postResponseDto)
                .collect(Collectors.toList());
    }

    public List<PostResponseDto> getMyCommentedPosts(User user) {
        return commentRepository.findByUser(user).stream()
                .map(Comment::getPost)
                .distinct()
                .map(this::postResponseDto)
                .collect(Collectors.toList());
    }

    public List<PostResponseDto> getMyScrappedPosts(User user) {
        return scrapRepository.findByUser(user).stream()
                .map(Scrap::getPost)
                .map(this::postResponseDto)
                .collect(Collectors.toList());
    }

    // 게시글 저장 메소드
    public void savePost(Post post) {
        postRepository.save(post);
    }

    public BoardResponseDto getPostsByBoard(Board board) {
        List<BoardResponseDto.ArticleResponseDto> articles = postRepository.findByBoard(board).stream()
                .map(post -> new BoardResponseDto.ArticleResponseDto(
                        post.getPostId(),
                        post.getTitle(),
                        post.getContent(),
                        post.getUser().getNickname(),
                        post.getRecommendCount(),
                        post.getCommentsCount()
                ))
                .collect(Collectors.toList());

        return new BoardResponseDto(board.getBoardId(), board.getBoardType().getDisplayName(), articles);
    }

    private PostResponseDto postResponseDto(Post post) {
        return new PostResponseDto(
                post.getBoard().getBoardId(),
                post.getBoard().getBoardType().getDisplayName(),
                post.getPostId(),
                post.getTitle(),
                post.getContent(),
                post.getUser().getNickname(),
                post.getRecommendCount(),
                post.getCommentsCount()
        );
    }
}
