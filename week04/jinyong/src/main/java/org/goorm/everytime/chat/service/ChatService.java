package org.goorm.everytime.chat.service;

import lombok.RequiredArgsConstructor;
import org.goorm.everytime.chat.api.dto.ChatDto;
import org.goorm.everytime.chat.api.dto.ChatRecordDto;
import org.goorm.everytime.chat.domain.ChatMessage;
import org.goorm.everytime.chat.domain.repository.ChatMessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;


    @Transactional
    public Mono<ChatMessage> saveChatMessage(ChatDto chat, Long chatroomId) {
        return chatMessageRepository.save(
                new ChatMessage(chatroomId, chat.newMessage(), chat.senderId(), new Date()));
    }


    @Transactional(readOnly = true)
    public Flux<ChatRecordDto> getChatRecordListDto(Long chatroomId) {
        return chatMessageRepository.findAllByRoomId(chatroomId)
                .map(chatMessage -> ChatRecordDto.builder()
                        .chatroomId(chatMessage.getRoomId())
                        .senderId(chatMessage.getSenderId())
                        .senderContent(chatMessage.getMessage())
                        .createdDate(chatMessage.getCreatedDate())
                        .build());
    }
}
