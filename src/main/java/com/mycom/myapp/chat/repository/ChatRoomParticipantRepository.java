package com.mycom.myapp.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycom.myapp.chat.entity.ChatRoomParticipant;

import java.util.List;

public interface ChatRoomParticipantRepository extends JpaRepository<ChatRoomParticipant, Long> {
    List<ChatRoomParticipant> findByUserEmail(String userEmail);
}
