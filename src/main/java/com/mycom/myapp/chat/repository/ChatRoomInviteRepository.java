package com.mycom.myapp.chat.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycom.myapp.chat.entity.ChatRoomInvite;
import com.mycom.myapp.chat.entity.InviteStatus;

public interface ChatRoomInviteRepository extends JpaRepository<ChatRoomInvite, Long> {
    /**
 * Retrieves a chat room invite by its unique ID and the associated user's email.
 *
 * @param id the unique identifier of the chat room invite
 * @param userEmail the email address of the user associated with the invite
 * @return an {@code Optional} containing the matching {@code ChatRoomInvite} if found, or empty if not found
 */
Optional<ChatRoomInvite> findByIdAndUserEmail(Long id, String userEmail);

    /****
 * Retrieves all chat room invites for a specific user with the given invite status.
 *
 * @param userEmail the email address of the user whose invites are to be retrieved
 * @param status the status to filter invites by
 * @return a list of chat room invites matching the user email and status
 */
List<ChatRoomInvite> findByUserEmailAndStatus(String userEmail, InviteStatus status);
}
