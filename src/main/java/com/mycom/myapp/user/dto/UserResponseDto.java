package com.mycom.myapp.user.dto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {
    private Long userId;
    private String userEmail;
    private String userName;

    /**
     * Constructs a UserResponseDto with the specified user ID, email, and name.
     *
     * @param userId    the unique identifier of the user
     * @param userEmail the email address of the user
     * @param userName  the name of the user
     */
    public UserResponseDto(Long userId, String userEmail, String userName) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.userName = userName;
    }
}
