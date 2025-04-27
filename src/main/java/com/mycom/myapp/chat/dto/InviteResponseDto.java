package com.mycom.myapp.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InviteResponseDto {
    private Long inviteId;
    private Long roomId;
    private String roomName;
}
