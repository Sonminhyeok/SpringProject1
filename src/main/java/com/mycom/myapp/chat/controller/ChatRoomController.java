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

    /**
     * Constructs a ChatRoomController with the specified chat room service.
     *
     * @param chatRoomService the service used to handle chat room operations
     */
    public ChatRoomController(ChatRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
    }

    /**
     * Creates a new chat room with the provided details, ensuring the current session user is included as a participant.
     *
     * Returns a 401 Unauthorized response if the user is not authenticated via session.
     *
     * @param chatRoomDto the chat room details from the request body
     * @return 200 OK with "success" if created, or 401 Unauthorized with "fail" if the user is not authenticated
     */
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

    /**
     * Retrieves all chat rooms.
     *
     * @return a response containing the list of all chat rooms
     */
    @GetMapping
    public ResponseEntity<List<ChatRoomDto>> getAllChatRooms() {
        return ResponseEntity.ok(chatRoomService.getAllChatRooms());
    }
    /**
     * Retrieves all chat rooms associated with the specified user.
     *
     * @param userEmail the email address of the user whose chat rooms are to be retrieved
     * @return a response containing the list of chat rooms for the user
     */
    @GetMapping("/my")
    public ResponseEntity<List<ChatRoomDto>> getMyChatRooms(@RequestParam String userEmail) {
        return ResponseEntity.ok(chatRoomService.getMyChatRooms(userEmail));
    }

}
