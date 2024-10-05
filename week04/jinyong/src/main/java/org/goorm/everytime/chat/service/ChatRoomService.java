package org.goorm.everytime.chat.service;

import lombok.RequiredArgsConstructor;
import org.goorm.everytime.board.domain.Post;
import org.goorm.everytime.board.domain.repository.PostRepository;
import org.goorm.everytime.chat.api.dto.ChatRoomInfoDto;
import org.goorm.everytime.chat.api.dto.ChatRoomsDto;
import org.goorm.everytime.chat.domain.ChatMessage;
import org.goorm.everytime.chat.domain.ChatRoom;
import org.goorm.everytime.chat.api.dto.ChatRoomReqDto;
import org.goorm.everytime.chat.domain.repository.ChatMessageRepository;
import org.goorm.everytime.chat.domain.repository.ChatRoomRepository;
import org.goorm.everytime.member.domain.Member;
import org.goorm.everytime.member.domain.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final PostRepository postRepository;
    //채팅방 생성 (postId로 채팅방이 존재하는지 확인)
    @Transactional
    public void createRoom(ChatRoomReqDto chatRoomReqDto, Long postId) {
        chatRoomRepository.findChatRoomByPostId(postId)
                .ifPresentOrElse(
                        chatRoom -> {
                            throw new IllegalArgumentException("이미 채팅방이 존재합니다.");
                        },
                        () -> {
                            chatRoomRepository.save(ChatRoom.of(chatRoomReqDto, postId));
                        }
                );
    }

    //채팅방 전체 조회
    @Transactional(readOnly = true)
    public ChatRoomsDto getChatRoomList() {
        List<ChatRoom> chatRooms = chatRoomRepository.findAll();
        List<ChatRoomInfoDto> chatRoomInfoDtos = chatRooms.stream().map(chatRoom -> {
            ChatMessage lastMessage = chatMessageRepository.findTopByRoomIdOrderByCreatedDateDesc(chatRoom.getId()).blockFirst();
            return new ChatRoomInfoDto(
                    chatRoom.getId(),
                    "익명",
                    lastMessage != null ? lastMessage.getMessage() : null,
                    lastMessage != null ? lastMessage.getCreatedDate() : null
            );
        }).collect(Collectors.toList());
        return new ChatRoomsDto(chatRoomInfoDtos);
    }
}
