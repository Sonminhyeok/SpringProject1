package com.mycom.myapp.chat.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycom.myapp.chat.dto.ChatRoomDto;
@Mapper
public interface ChatRoomDao {
    void insertChatRoom(ChatRoomDto chatRoomDto);
    void insertChatUser(Long roomId, String userEmail);
    List<ChatRoomDto> selectAllChatRooms();
    List<String> selectParticipantsByRoomId(Long roomId);
}
