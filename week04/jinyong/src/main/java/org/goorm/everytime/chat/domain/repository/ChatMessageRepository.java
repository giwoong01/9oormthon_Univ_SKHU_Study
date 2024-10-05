package org.goorm.everytime.chat.domain.repository;

import org.goorm.everytime.chat.domain.ChatMessage;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ChatMessageRepository extends ReactiveMongoRepository<ChatMessage, String> {
    Flux<ChatMessage> findAllByRoomId(Long roomId);

    Flux<ChatMessage> findTopByRoomIdOrderByCreatedDateDesc(Long id);
}
