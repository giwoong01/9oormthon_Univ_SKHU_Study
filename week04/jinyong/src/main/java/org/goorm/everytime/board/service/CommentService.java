package org.goorm.everytime.board.service;

import lombok.RequiredArgsConstructor;
import org.goorm.everytime.board.domain.repository.CommentRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    //내가 쓴 댓글 조회 - 정확히는 내가 댓글 쓴 게시글 조회
}
