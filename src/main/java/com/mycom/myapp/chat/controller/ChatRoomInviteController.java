package com.mycom.myapp.chat.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycom.myapp.chat.dto.AcceptInviteRequestDto;
import com.mycom.myapp.chat.dto.InviteRequestDto;
import com.mycom.myapp.chat.dto.InviteResponseDto;
import com.mycom.myapp.chat.service.ChatRoomInviteService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/chatroom/invite")
public class ChatRoomInviteController {

    private final ChatRoomInviteService chatRoomInviteService;

    public ChatRoomInviteController(ChatRoomInviteService chatRoomInviteService) {
        this.chatRoomInviteService = chatRoomInviteService;
    }

    @PostMapping
    public ResponseEntity<String> inviteUser(@RequestBody InviteRequestDto requestDto) {
        chatRoomInviteService.inviteUser(requestDto);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/accept")
    public ResponseEntity<String> acceptInvite(@RequestBody AcceptInviteRequestDto requestDto, HttpSession session) {
        String userEmail = (String) session.getAttribute("userEmail"); 
        if (userEmail == null) {
            return ResponseEntity.status(401).body("fail");
        }
        chatRoomInviteService.acceptInvite(requestDto.getInviteId(), userEmail);
        return ResponseEntity.ok("success");
    }
    @PostMapping("/reject")
    public ResponseEntity<String> rejectInvite(@RequestBody AcceptInviteRequestDto requestDto, HttpSession session) {
        String userEmail = (String) session.getAttribute("userEmail");
        if (userEmail == null) {
            return ResponseEntity.status(401).body("fail");
        }
        chatRoomInviteService.rejectInvite(requestDto.getInviteId(), userEmail);
        return ResponseEntity.ok("success");
    }
    @GetMapping("/pending")
    public ResponseEntity<List<InviteResponseDto>> getPendingInvites(@RequestParam String userEmail) {
        return ResponseEntity.ok(chatRoomInviteService.getPendingInvites(userEmail));
    }


}
