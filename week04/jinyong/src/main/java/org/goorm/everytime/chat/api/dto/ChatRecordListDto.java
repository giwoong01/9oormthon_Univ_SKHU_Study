package org.goorm.everytime.chat.api.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ChatRecordListDto(
        List<ChatRecordDto> messages
) {
}
