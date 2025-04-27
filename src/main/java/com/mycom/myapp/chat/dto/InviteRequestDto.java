package com.mycom.myapp.chat.dto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InviteRequestDto {
    private Long roomId;
    private String userEmail;
}
