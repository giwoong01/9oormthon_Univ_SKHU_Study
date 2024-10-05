package org.goorm.everytime.chat.api;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.goorm.everytime.chat.api.dto.ChatDto;
import org.goorm.everytime.chat.api.dto.ChatRecordListDto;
import org.goorm.everytime.chat.service.ChatService;
import org.goorm.everytime.global.common.dto.BaseResponse;
import org.goorm.everytime.global.common.exception.SuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;

    @Operation(summary = "메시지 전송", description = "메시지를 전송한다.")
    @MessageMapping("/{chatroomId}")
    public Mono<ResponseEntity<Void>> sendMessage(@DestinationVariable("chatroomId") Long chatroomId , @Payload ChatDto chat) {
        return chatService.saveChatMessage(chat, chatroomId)
                .map(chatMessage -> {
                    messagingTemplate.convertAndSend("/sub/" + chatroomId, chatMessage);
                    return ResponseEntity.ok().build();
                });
    }

    @GetMapping("/messages")
    public Mono<BaseResponse<ChatRecordListDto>> getMessages(@RequestParam Long chatRoomId) {
        return chatService.getChatRecordListDto(chatRoomId)
                .collectList()
                .map(ChatRecordListDto::new)
                .map(chatRecordListDto -> BaseResponse.success(SuccessCode.GET_CHAT_RECORD_LIST, chatRecordListDto));
    }
}
