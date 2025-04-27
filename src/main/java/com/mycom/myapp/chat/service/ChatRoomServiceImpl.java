package com.mycom.myapp.chat.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycom.myapp.chat.dto.ChatRoomDto;
import com.mycom.myapp.chat.entity.ChatRoom;
import com.mycom.myapp.chat.entity.ChatRoomParticipant;
import com.mycom.myapp.chat.repository.ChatRoomRepository;
import com.mycom.myapp.chat.repository.ChatRoomParticipantRepository;

@Service
@Transactional
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomParticipantRepository chatRoomParticipantRepository;

    public ChatRoomServiceImpl(ChatRoomRepository chatRoomRepository, ChatRoomParticipantRepository chatRoomParticipantRepository) {
        this.chatRoomRepository = chatRoomRepository;
        this.chatRoomParticipantRepository = chatRoomParticipantRepository;
    }

    @Override
    public void createChatRoom(ChatRoomDto chatRoomDto) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setRoomName(chatRoomDto.getRoomName());

        List<ChatRoomParticipant> participants = new ArrayList<>();
        for (String email : chatRoomDto.getParticipants()) {
            ChatRoomParticipant participant = new ChatRoomParticipant();
            participant.setUserEmail(email);
            participant.setChatRoom(chatRoom);
            participants.add(participant);
        }

        chatRoom.setParticipants(participants);
        chatRoomRepository.save(chatRoom);
    }

    @Override
    public List<ChatRoomDto> getAllChatRooms() {
        List<ChatRoom> rooms = chatRoomRepository.findAll();

        return rooms.stream().map(room -> {
            ChatRoomDto dto = new ChatRoomDto();
            dto.setRoomId(room.getRoomId());
            dto.setRoomName(room.getRoomName());
            dto.setParticipants(
                room.getParticipants().stream()
                    .map(ChatRoomParticipant::getUserEmail)
                    .collect(Collectors.toList())
            );
            return dto;
        }).collect(Collectors.toList());
    }
    @Override
    public List<ChatRoomDto> getMyChatRooms(String userEmail) {
        List<ChatRoomParticipant> participantList = chatRoomParticipantRepository.findByUserEmail(userEmail);

        return participantList.stream()
            .map(ChatRoomParticipant::getChatRoom)
            .distinct()
            .map(room -> {
                ChatRoomDto dto = new ChatRoomDto();
                dto.setRoomId(room.getRoomId());
                dto.setRoomName(room.getRoomName());
                dto.setParticipants(
                    room.getParticipants().stream()
                        .map(ChatRoomParticipant::getUserEmail)
                        .collect(Collectors.toList())
                );
                return dto;
            })
            .collect(Collectors.toList());
    }

}
