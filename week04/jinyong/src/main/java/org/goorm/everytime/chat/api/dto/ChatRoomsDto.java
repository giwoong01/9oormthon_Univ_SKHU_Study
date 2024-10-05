package org.goorm.everytime.chat.api.dto;

import java.util.List;

public record ChatRoomsDto(
        List<ChatRoomInfoDto> chatRoom
) {
}
