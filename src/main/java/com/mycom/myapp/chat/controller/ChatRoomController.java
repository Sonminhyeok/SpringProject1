package com.mycom.myapp.chat.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycom.myapp.chat.dto.ChatRoomDto;
import com.mycom.myapp.chat.service.ChatRoomService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/chatroom")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    public ChatRoomController(ChatRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
    }

    @PostMapping
    public ResponseEntity<String> createChatRoom(@RequestBody ChatRoomDto chatRoomDto, HttpSession session) {
        String userEmail = (String) session.getAttribute("userEmail");
        if (userEmail == null) {
            return ResponseEntity.status(401).body("fail");
        }

  
        List<String> participants = new ArrayList<>(chatRoomDto.getParticipants());
        if (!participants.contains(userEmail)) {
            participants.add(userEmail);
        }
        chatRoomDto.setParticipants(participants);

        chatRoomService.createChatRoom(chatRoomDto);
        return ResponseEntity.ok("success");
    }

    @GetMapping
    public ResponseEntity<List<ChatRoomDto>> getAllChatRooms() {
        return ResponseEntity.ok(chatRoomService.getAllChatRooms());
    }
    @GetMapping("/my")
    public ResponseEntity<List<ChatRoomDto>> getMyChatRooms(@RequestParam String userEmail) {
        return ResponseEntity.ok(chatRoomService.getMyChatRooms(userEmail));
    }

}
