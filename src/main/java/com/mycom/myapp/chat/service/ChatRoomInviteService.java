package com.mycom.myapp.chat.service;

import java.util.List;

import com.mycom.myapp.chat.dto.AcceptInviteRequestDto;
import com.mycom.myapp.chat.dto.InviteRequestDto;
import com.mycom.myapp.chat.dto.InviteResponseDto;

public interface ChatRoomInviteService {
    void inviteUser(InviteRequestDto requestDto);
    void acceptInvite(Long inviteId, String userEmail);
    void rejectInvite(Long inviteId, String userEmail);
    List<InviteResponseDto> getPendingInvites(String userEmail);

}
