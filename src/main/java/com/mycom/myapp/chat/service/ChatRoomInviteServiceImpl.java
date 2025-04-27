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

    public ChatRoomInviteServiceImpl(ChatRoomRepository chatRoomRepository,
                                     ChatRoomInviteRepository chatRoomInviteRepository,
                                     ChatRoomParticipantRepository chatRoomParticipantRepository) {
        this.chatRoomRepository = chatRoomRepository;
        this.chatRoomInviteRepository = chatRoomInviteRepository;
        this.chatRoomParticipantRepository = chatRoomParticipantRepository;
    }

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

    @Override
    public void rejectInvite(Long inviteId, String userEmail) {
        ChatRoomInvite invite = chatRoomInviteRepository.findByIdAndUserEmail(inviteId, userEmail)
                .orElseThrow(() -> new IllegalArgumentException("fail"));

        if (invite.getStatus() != InviteStatus.PENDING) {
            throw new IllegalStateException("fail");
        }

        invite.setStatus(InviteStatus.REJECTED);
    }
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
