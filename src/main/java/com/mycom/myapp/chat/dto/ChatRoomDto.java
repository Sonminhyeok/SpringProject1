package com.mycom.myapp.chat.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ChatRoomDto {
    private Long roomId;
    private String roomName;
    private List<String> participants;

    
}
