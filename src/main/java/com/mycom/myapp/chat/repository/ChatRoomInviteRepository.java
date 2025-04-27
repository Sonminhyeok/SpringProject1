package com.mycom.myapp.chat.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycom.myapp.chat.entity.ChatRoomInvite;
import com.mycom.myapp.chat.entity.InviteStatus;

public interface ChatRoomInviteRepository extends JpaRepository<ChatRoomInvite, Long> {
    Optional<ChatRoomInvite> findByIdAndUserEmail(Long id, String userEmail);

    List<ChatRoomInvite> findByUserEmailAndStatus(String userEmail, InviteStatus status);
}
