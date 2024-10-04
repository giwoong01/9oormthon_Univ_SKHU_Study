package org.skhu.everytime.community.board.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.skhu.everytime.user.User;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "게시글 ID", example = "1")
    private Long postId;

    @Schema(description = "게시글 제목", example = "첫 번째 게시글")
    private String title;

    @Schema(description = "게시글 내용", example = "게시글 내용")
    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    @Schema(description = "게시글이 속한 게시판", example = "자유게시판")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    @Schema(description = "게시글 작성자")
    private User user;

    @Schema(description = "게시글 추천 수", example = "15")
    private int recommendCount;

    @Schema(description = "댓글 수", example = "5")
    private int commentsCount;

    // 게시글 생성자 (생성 시 필요한 데이터만 초기화)
    public Post(String title, String content, Board board, User user) {
        this.title = title;
        this.content = content;
        this.board = board;
        this.user = user;
        this.recommendCount = 0; // 기본 추천 수는 0
        this.commentsCount = 0; // 기본 댓글 수는 0
    }

    // 게시글 업데이트 메소드 (내용 수정 가능)
    public void updatePost(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // 추천 수 증가
    public void increaseRecommendCount() {
        this.recommendCount++;
    }

    // 댓글 수 증가
    public void increaseCommentCount() {
        this.commentsCount++;
    }

    // 게시판 설정
    public void updateBoard(Board board){
        this.board = board;
    }

}
