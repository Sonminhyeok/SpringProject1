package com.mycom.myapp.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRegisterRequestDto {
    private String userEmail;
    private String userPassword;
    private String userName;
}
