package org.goorm.everytime.board.service.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.goorm.everytime.board.domain.Boards;
import org.goorm.everytime.board.domain.repository.BoardsRespository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

// 게시판 테이블 데이터 초기화

@Service
@RequiredArgsConstructor
public class BoardsDataUtil {

    private final BoardsRespository boardsRespository;
    private final TransactionTemplate transactionTemplate;

    @PostConstruct
    public void loadSaveData() {
        List<String> boardNames = List.of(
                "자유게시판",
                "비밀게시판",
                "졸업생게시판",
                "새내기게시판",
                "시사•이슈",
                "정보게시판",
                "취업•진로",
                "홍보게시판",
                "동아리•학회",
                "미디어센터",
                "기숙사게시판"
        );
        transactionTemplate.execute(status -> {
            Boards boardsName;
            for (String boardName : boardNames) {
                boardsName = Boards.builder()
                        .boardName(boardName)
                        .build();
                boardsRespository.save(boardsName);
            }
            return null;
        });
    }
}
