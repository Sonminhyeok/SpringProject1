package com.mycom.myapp.chat.service;

import java.util.List;

import com.mycom.myapp.chat.dto.ChatRoomDto;

public interface ChatRoomService {
    void createChatRoom(ChatRoomDto chatRoomDto);
    List<ChatRoomDto> getAllChatRooms();
}

