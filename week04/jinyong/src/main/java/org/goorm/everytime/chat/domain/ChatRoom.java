package org.goorm.everytime.chat.domain;

import jakarta.persistence.*;
import lombok.*;
import org.goorm.everytime.chat.api.dto.ChatRoomReqDto;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatroom_id")
    private Long id;

    //쪽지는 자기 자신에게 보낼 수 없으니
    //쪽지를 받는이receiverId를 masterId로 함.
    private Long masterId;

    //쪽지를 먼저 보내야 채팅이 활성화되므로
    //쪽지를 보내는이senderId를 talkerId로 함.
    private Long talkerId;

    //채팅방을 구분하기 위한 postId
    private Long postId;

    private Date createdAt;

    public static ChatRoom of(ChatRoomReqDto chatRoomReqDto, Long postId) {
        return ChatRoom.builder()
                .masterId(chatRoomReqDto.receiverId())
                .talkerId(chatRoomReqDto.senderId())
                .postId(postId)
                .createdAt(new Date())
                .build();
    }
}
