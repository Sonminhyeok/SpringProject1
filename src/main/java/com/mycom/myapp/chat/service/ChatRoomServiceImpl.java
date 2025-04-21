package com.mycom.myapp.chat.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mycom.myapp.chat.dao.ChatRoomDao;
import com.mycom.myapp.chat.dto.ChatRoomDto;

@Service
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomDao chatRoomDao;

    public ChatRoomServiceImpl(ChatRoomDao chatRoomDao) {
        this.chatRoomDao = chatRoomDao;
    }

    @Override
    public void createChatRoom(ChatRoomDto chatRoomDto) {
        chatRoomDao.insertChatRoom(chatRoomDto);
        for (String email : chatRoomDto.getParticipants()) {
            chatRoomDao.insertChatUser(chatRoomDto.getRoomId(), email);
        }
    }

    @Override
    public List<ChatRoomDto> getAllChatRooms() {
        List<ChatRoomDto> list = chatRoomDao.selectAllChatRooms();

        for (ChatRoomDto room : list) {
            List<String> participants = chatRoomDao.selectParticipantsByRoomId(room.getRoomId());
            room.setParticipants(participants);
        }

        return list;
    }
}
