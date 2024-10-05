package org.goorm.everytime.chat.api.dto;

import lombok.Builder;

@Builder
public record ChatDto(
        Long receiverId,
        Long senderId,
        String newMessage
) {
}
