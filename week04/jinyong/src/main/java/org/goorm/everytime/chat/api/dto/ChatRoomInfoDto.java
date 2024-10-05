package org.goorm.everytime.chat.api.dto;

import java.util.Date;

public record ChatRoomInfoDto(
        Long chatRoomId,
        String author,
        String lastMessage,
        Date timeStamp //lastMessageTimeStamp
         ){
}
