package com.mycom.myapp.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycom.myapp.chat.entity.ChatRoomParticipant;

import java.util.List;

public interface ChatRoomParticipantRepository extends JpaRepository<ChatRoomParticipant, Long> {
    /**
 * Retrieves all chat room participants associated with the specified user email.
 *
 * @param userEmail the email address of the user whose chat room participants are to be found
 * @return a list of ChatRoomParticipant entities linked to the given user email
 */
List<ChatRoomParticipant> findByUserEmail(String userEmail);
}
