package org.goorm.everytime.chat.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import org.goorm.everytime.chat.api.dto.ChatRoomsDto;
import org.goorm.everytime.chat.service.ChatRoomService;
import org.goorm.everytime.chat.api.dto.ChatRoomReqDto;
import org.goorm.everytime.global.common.dto.BaseResponse;
import org.goorm.everytime.global.common.exception.SuccessCode;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    //채팅방 생성
    @Operation(summary = "채팅방 생성", description = "채팅방을 생성한다.")
    @Parameters({
            @Parameter(name = "boardId", description = "게시판 ID", required = true),
            @Parameter(name = "postId", description = "게시글 ID", required = true),
            @Parameter(name = "chatRoomReqDto", description = "채팅방 생성 정보", required = true)
    })
    @PostMapping("/boards/{boardId}/posts/{postId}/chatRoom")
    public BaseResponse createRoom(@PathVariable Long postId, @RequestBody ChatRoomReqDto chatRoomReqDto){
        chatRoomService.createRoom(chatRoomReqDto, postId);
        return BaseResponse.success(SuccessCode.CHAT_ROOM_CREATED);
    }

    @Operation(summary = "채팅방 조회", description = "채팅방을 조회한다.")
    //채팅방 조회
    @GetMapping("/chatRoom")
    public BaseResponse<ChatRoomsDto> getChatRoom(){
        return BaseResponse.success(SuccessCode.GET_CHAT_ROOM, chatRoomService.getChatRoomList());
    }
}
