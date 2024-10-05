package org.goorm.everytime.chat.api.dto;

import lombok.Builder;

@Builder
public record ChatRoomReqDto(
        String newMessage,
        Long senderId,
        Long receiverId
) {
}
