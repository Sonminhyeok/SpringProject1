package com.mycom.myapp.chat.service;

import java.util.List;

import com.mycom.myapp.chat.dto.AcceptInviteRequestDto;
import com.mycom.myapp.chat.dto.InviteRequestDto;
import com.mycom.myapp.chat.dto.InviteResponseDto;

public interface ChatRoomInviteService {
    /**
 * Sends an invitation to a user to join a chat room based on the provided request data.
 *
 * @param requestDto the invitation request details
 */
void inviteUser(InviteRequestDto requestDto);
    /**
 * Accepts a chat room invitation for the specified user.
 *
 * @param inviteId the unique identifier of the invitation to accept
 * @param userEmail the email address of the user accepting the invitation
 */
void acceptInvite(Long inviteId, String userEmail);
    /**
 * Rejects a chat room invitation for the specified user.
 *
 * @param inviteId the unique identifier of the invitation to reject
 * @param userEmail the email address of the user rejecting the invitation
 */
void rejectInvite(Long inviteId, String userEmail);
    /**
 * Retrieves all pending chat room invitations for the specified user.
 *
 * @param userEmail the email address of the user whose pending invitations are to be fetched
 * @return a list of pending invitation details for the user
 */
List<InviteResponseDto> getPendingInvites(String userEmail);

}
