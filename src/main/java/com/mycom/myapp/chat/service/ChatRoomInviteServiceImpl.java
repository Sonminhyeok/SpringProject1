package com.mycom.myapp.chat.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycom.myapp.chat.dto.AcceptInviteRequestDto;
import com.mycom.myapp.chat.dto.InviteRequestDto;
import com.mycom.myapp.chat.dto.InviteResponseDto;
import com.mycom.myapp.chat.entity.ChatRoom;
import com.mycom.myapp.chat.entity.ChatRoomInvite;
import com.mycom.myapp.chat.entity.ChatRoomParticipant;
import com.mycom.myapp.chat.entity.InviteStatus;
import com.mycom.myapp.chat.repository.ChatRoomInviteRepository;
import com.mycom.myapp.chat.repository.ChatRoomParticipantRepository;
import com.mycom.myapp.chat.repository.ChatRoomRepository;

@Service
@Transactional
public class ChatRoomInviteServiceImpl implements ChatRoomInviteService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomInviteRepository chatRoomInviteRepository;
    private final ChatRoomParticipantRepository chatRoomParticipantRepository;

    /**
     * Constructs a new ChatRoomInviteServiceImpl with the specified repositories.
     *
     * @param chatRoomRepository repository for chat room entities
     * @param chatRoomInviteRepository repository for chat room invite entities
     * @param chatRoomParticipantRepository repository for chat room participant entities
     */
    public ChatRoomInviteServiceImpl(ChatRoomRepository chatRoomRepository,
                                     ChatRoomInviteRepository chatRoomInviteRepository,
                                     ChatRoomParticipantRepository chatRoomParticipantRepository) {
        this.chatRoomRepository = chatRoomRepository;
        this.chatRoomInviteRepository = chatRoomInviteRepository;
        this.chatRoomParticipantRepository = chatRoomParticipantRepository;
    }

    /**
     * Creates a pending invitation for a user to join a specified chat room.
     *
     * @param requestDto contains the chat room ID and the user's email to be invited
     * @throws IllegalArgumentException if the specified chat room does not exist
     */
    @Override
    public void inviteUser(InviteRequestDto requestDto) {
        ChatRoom chatRoom = chatRoomRepository.findById(requestDto.getRoomId())
                .orElseThrow(() -> new IllegalArgumentException("fail"));

        ChatRoomInvite invite = new ChatRoomInvite();
        invite.setChatRoom(chatRoom);
        invite.setUserEmail(requestDto.getUserEmail());
        invite.setStatus(InviteStatus.PENDING);

        chatRoomInviteRepository.save(invite);
    }

    /**
     * Accepts a pending chat room invitation for the specified user.
     *
     * Updates the invitation status to ACCEPTED and adds the user as a participant in the chat room.
     *
     * @param inviteId the ID of the invitation to accept
     * @param userEmail the email of the user accepting the invitation
     * @throws IllegalArgumentException if the invitation does not exist for the given user
     * @throws IllegalStateException if the invitation is not in PENDING status
     */
    @Override
    public void acceptInvite(Long inviteId, String userEmail) {
        ChatRoomInvite invite = chatRoomInviteRepository.findByIdAndUserEmail(inviteId, userEmail)
                .orElseThrow(() -> new IllegalArgumentException("fail"));

        if (invite.getStatus() != InviteStatus.PENDING) {
            throw new IllegalStateException("fail");
        }

        invite.setStatus(InviteStatus.ACCEPTED);

        ChatRoomParticipant participant = new ChatRoomParticipant();
        participant.setChatRoom(invite.getChatRoom());
        participant.setUserEmail(invite.getUserEmail());
        chatRoomParticipantRepository.save(participant);
    }

    /**
     * Rejects a pending chat room invitation for the specified user.
     *
     * @param inviteId the ID of the invitation to reject
     * @param userEmail the email of the user rejecting the invitation
     * @throws IllegalArgumentException if the invitation does not exist for the user
     * @throws IllegalStateException if the invitation is not in a pending state
     */
    @Override
    public void rejectInvite(Long inviteId, String userEmail) {
        ChatRoomInvite invite = chatRoomInviteRepository.findByIdAndUserEmail(inviteId, userEmail)
                .orElseThrow(() -> new IllegalArgumentException("fail"));

        if (invite.getStatus() != InviteStatus.PENDING) {
            throw new IllegalStateException("fail");
        }

        invite.setStatus(InviteStatus.REJECTED);
    }
    /**
     * Retrieves all pending chat room invitations for the specified user.
     *
     * @param userEmail the email address of the user whose pending invites are to be fetched
     * @return a list of pending invite response DTOs containing invite and chat room details
     */
    @Override
    public List<InviteResponseDto> getPendingInvites(String userEmail) {
        List<ChatRoomInvite> invites = chatRoomInviteRepository.findByUserEmailAndStatus(userEmail, InviteStatus.PENDING);

        return invites.stream().map(invite -> new InviteResponseDto(
                invite.getId(),
                invite.getChatRoom().getRoomId(),
                invite.getChatRoom().getRoomName()
        )).collect(Collectors.toList());
    }


}
