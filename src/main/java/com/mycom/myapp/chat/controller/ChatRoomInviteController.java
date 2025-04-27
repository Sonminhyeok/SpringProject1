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

    /**
     * Constructs a new ChatRoomInviteController with the specified invite service.
     *
     * @param chatRoomInviteService the service handling chat room invitation logic
     */
    public ChatRoomInviteController(ChatRoomInviteService chatRoomInviteService) {
        this.chatRoomInviteService = chatRoomInviteService;
    }

    /**
     * Handles a request to invite a user to a chat room.
     *
     * @param requestDto the invitation details
     * @return HTTP 200 response with "success" upon successful invitation
     */
    @PostMapping
    public ResponseEntity<String> inviteUser(@RequestBody InviteRequestDto requestDto) {
        chatRoomInviteService.inviteUser(requestDto);
        return ResponseEntity.ok("success");
    }

    /**
     * Accepts a chat room invitation for the authenticated user.
     *
     * If the user's email is not found in the session, returns a 401 Unauthorized response with "fail".
     * Otherwise, processes the invitation acceptance and returns a 200 OK response with "success".
     *
     * @param requestDto the invitation acceptance request containing the invite ID
     * @return HTTP 200 with "success" if accepted, or HTTP 401 with "fail" if the user is not authenticated
     */
    @PostMapping("/accept")
    public ResponseEntity<String> acceptInvite(@RequestBody AcceptInviteRequestDto requestDto, HttpSession session) {
        String userEmail = (String) session.getAttribute("userEmail"); 
        if (userEmail == null) {
            return ResponseEntity.status(401).body("fail");
        }
        chatRoomInviteService.acceptInvite(requestDto.getInviteId(), userEmail);
        return ResponseEntity.ok("success");
    }
    /**
     * Rejects a chat room invitation for the authenticated user.
     *
     * Returns HTTP 200 with "success" if the invite is successfully rejected, or HTTP 401 with "fail" if the user is not authenticated.
     *
     * @param requestDto contains the invite ID to reject
     * @return HTTP response indicating the result of the rejection
     */
    @PostMapping("/reject")
    public ResponseEntity<String> rejectInvite(@RequestBody AcceptInviteRequestDto requestDto, HttpSession session) {
        String userEmail = (String) session.getAttribute("userEmail");
        if (userEmail == null) {
            return ResponseEntity.status(401).body("fail");
        }
        chatRoomInviteService.rejectInvite(requestDto.getInviteId(), userEmail);
        return ResponseEntity.ok("success");
    }
    /**
     * Retrieves the list of pending chat room invitations for the specified user.
     *
     * @param userEmail the email address of the user whose pending invites are requested
     * @return a response containing the list of pending invitations for the user
     */
    @GetMapping("/pending")
    public ResponseEntity<List<InviteResponseDto>> getPendingInvites(@RequestParam String userEmail) {
        return ResponseEntity.ok(chatRoomInviteService.getPendingInvites(userEmail));
    }


}
