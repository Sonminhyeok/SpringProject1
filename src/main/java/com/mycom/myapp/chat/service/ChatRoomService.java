package com.mycom.myapp.chat.service;

import java.util.List;

import com.mycom.myapp.chat.dto.ChatRoomDto;

import java.util.List;

import com.mycom.myapp.chat.dto.ChatRoomDto;

public interface ChatRoomService {
    /**
 * Creates a new chat room using the provided chat room data.
 *
 * @param chatRoomDto the data transfer object containing information for the new chat room
 */
void createChatRoom(ChatRoomDto chatRoomDto);
    /**
 * Retrieves a list of all chat rooms.
 *
 * @return a list containing all chat rooms as ChatRoomDto objects
 */
List<ChatRoomDto> getAllChatRooms();
    /****
 * Retrieves a list of chat rooms associated with the specified user's email address.
 *
 * @param userEmail the email address identifying the user
 * @return a list of chat rooms the user is a member of
 */
List<ChatRoomDto> getMyChatRooms(String userEmail);

}

