package org.goorm.everytime.chat.domain;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Document(collection = "chatting_content")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    @Id
    private ObjectId id;
    private Long roomId;
    private String message;
    private Long senderId;
    private Date createdDate;

    public ChatMessage(Long roomId, String message, Long senderId, Date createdDate) {
        this.roomId = roomId;
        this.message = message;
        this.senderId = senderId;
        this.createdDate = createdDate;
    }
}