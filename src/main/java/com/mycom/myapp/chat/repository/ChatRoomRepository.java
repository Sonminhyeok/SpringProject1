package com.mycom.myapp.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycom.myapp.chat.entity.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
