package com.mycom.myapp.auth.service;

import java.util.Optional;

import com.mycom.myapp.user.dto.UserLoginRequestDto;
import com.mycom.myapp.user.dto.UserRegisterRequestDto;
import com.mycom.myapp.user.dto.UserUpdateRequestDto;
import com.mycom.myapp.user.entity.User;

public interface LoginService {
    /****
 * Attempts to authenticate a user with the provided login credentials.
 *
 * @param requestDto the user login request containing authentication details
 * @return an Optional containing the authenticated User if successful, or empty if authentication fails
 */
Optional<User> login(UserLoginRequestDto requestDto);
    /**
 * Registers a new user with the provided registration details.
 *
 * @param requestDto the user registration information
 * @return true if registration is successful, false otherwise
 */
boolean register(UserRegisterRequestDto requestDto);
    /**
 * Updates the profile information of a user.
 *
 * @param requestDto the data transfer object containing updated user profile information
 * @return true if the profile update was successful, false otherwise
 */
boolean updateProfile(UserUpdateRequestDto requestDto);
}
