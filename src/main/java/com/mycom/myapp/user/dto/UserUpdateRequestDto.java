package com.mycom.myapp.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdateRequestDto {
    private Long userId; 
    private String userName;
    private String userPassword;
}
