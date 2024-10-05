package org.goorm.everytime.chat.api.dto;

import lombok.Builder;

import java.util.Date;

@Builder
public record ChatRecordDto(
        Long chatroomId,
        Long senderId,
        Long receiverId,
        String senderContent,
        String receiverContent,
        Date createdDate
) {
}
