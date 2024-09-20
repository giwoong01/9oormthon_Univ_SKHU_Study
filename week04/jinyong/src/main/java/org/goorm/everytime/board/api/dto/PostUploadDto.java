package org.goorm.everytime.board.api.dto;

import org.springframework.web.multipart.MultipartFile;

public record PostUploadDto(String title,
                            String content,
                            boolean anonym,
                            MultipartFile[] files,
                            Long boardId) {
}
