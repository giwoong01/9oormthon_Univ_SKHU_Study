package com.example.everytime.post.applicatioin;

import com.example.everytime.board.domain.Board;
import com.example.everytime.board.domain.repository.BoardRepository;
import com.example.everytime.member.domain.Member;
import com.example.everytime.member.domain.repository.MemberRepository;
import com.example.everytime.post.api.dto.request.CommentReqDto;
import com.example.everytime.post.api.dto.request.PostSaveReqDto;
import com.example.everytime.post.api.dto.response.MyPostsResDto;
import com.example.everytime.post.api.dto.response.PostSaveResDto;
import com.example.everytime.post.domain.Comment;
import com.example.everytime.post.domain.Post;
import com.example.everytime.post.domain.repository.CommentRepository;
import com.example.everytime.post.domain.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void savePost(long boardId, Member member, PostSaveReqDto postSaveReqDto) {
        if (member == null) {
            throw new IllegalArgumentException("회원 정보가 없습니다.");
        }

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시판이 존재하지 않습니다."));

        boolean isAnonym = Boolean.parseBoolean(postSaveReqDto.anonym());
        String author = isAnonym ? "익명" : member.getName();

        Post post = Post.builder()
                .title(postSaveReqDto.title())
                .content(postSaveReqDto.content())
                .author(author)
                .board(board)
                .member(member)
                .build();

        Post savedPost = postRepository.save(post);
        new PostSaveResDto(savedPost.getPostId(), savedPost.getTitle(), savedPost.getContent(), savedPost.getAuthor());
    }


    public MyPostsResDto getMyPosts(Member member) {
        List<Post> posts = postRepository.findByAuthor(member.getName());
        List<MyPostsResDto.PostDto> postDtos = posts.stream()
                .map(post -> new MyPostsResDto.PostDto(
                        post.getPostId(),
                        post.getTitle(),
                        post.getContent(),
                        post.getAuthor(),
                        post.getVotes(),
                        post.getComments()
                ))
                .collect(Collectors.toList());
        return new MyPostsResDto(postDtos);
    }

    @Transactional
    public void votePost(Long postId, Member member) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));
        post.incrementVotes();
    }

    @Transactional
    public void addComment(Long postId, CommentReqDto commentDto, Member member) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));

        Comment comment = Comment.builder()
                .content(commentDto.content())
                .author(commentDto.author())
                .post(post)
                .build();

        commentRepository.save(comment);
        post.incrementComments();
    }

    @Transactional
    public void addScrap(Long boardId, Long postId, Member member) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));

        post.incrementScraps();
        postRepository.save(post);
    }
}
